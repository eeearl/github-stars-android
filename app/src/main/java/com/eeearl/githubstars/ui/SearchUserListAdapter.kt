package com.eeearl.githubstars.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.eeearl.githubstars.BR
import com.eeearl.githubstars.R
import com.eeearl.githubstars.db.DatabaseService
import com.eeearl.githubstars.util.CharUtil
import com.eeearl.githubstars.util.KoreanCharacterUtil
import com.eeearl.githubstars.util.OrderingByKorean
import java.util.*
import kotlin.collections.ArrayList

enum class SearchUserType(val rawValue: Int) {
    SECTION(1), REMOTE_ITEM(2), LOCAL_ITEM(3);

    companion object {
        fun valueOf(value: Int) = entries.find { it.rawValue == value }
    }
}

class SearchUserListAdapter(
    private val mList: ArrayList<SearchUserRowItemType>
): RecyclerView.Adapter<SearchUserListAdapter.ViewHolder>() {

    class ViewHolder (private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindSection(position: Int, obj: SearchUserRowSection) {
            binding.setVariable(BR.sectionName, obj.section)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }

        fun bindItem(position: Int, obj: SearchUserRowRemoteItem, adapter: SearchUserListAdapter) {
            binding.setVariable(BR.data, obj)
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.adapter, adapter)
            binding.executePendingBindings()
        }

        fun bindLocalItem(position: Int, obj: SearchUserRowLocalItem, adapter: SearchUserListAdapter) {
            binding.setVariable(BR.data, obj)
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.adapter, adapter)
            binding.executePendingBindings()
        }
    }

    private var mSection: MutableMap<Char, Int> = mutableMapOf()

    override fun getItemViewType(position: Int): Int {
        val item: SearchUserRowItemType = mList[position]
        return item.itemType.rawValue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val type = SearchUserType.valueOf(viewType)
        when (type) {
            SearchUserType.SECTION -> {
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    inflater, R.layout.row_search_user_section, parent, false)

                return ViewHolder(binding)
            }
            SearchUserType.REMOTE_ITEM -> {
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    inflater, R.layout.row_search_user_remote_item, parent, false)

                return ViewHolder(binding)
            }
            else -> { // SearchUserType.LOCAL_ITEM
                val binding: ViewDataBinding = DataBindingUtil.inflate(
                    inflater, R.layout.row_search_user_local_item, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: SearchUserRowItemType = mList[position]
        when (item.itemType) {
            SearchUserType.SECTION -> holder.bindSection(position, item as SearchUserRowSection)
            SearchUserType.REMOTE_ITEM -> holder.bindItem(position, item as SearchUserRowRemoteItem, this)
            SearchUserType.LOCAL_ITEM -> holder.bindLocalItem(position, item as SearchUserRowLocalItem, this)
        }
    }

    /**
     * Only uses in Local Fragment
     */
    fun onClickLocalFavoriteCheckbox(item: SearchUserRowLocalItem, context: Context) {
        val database = DatabaseService.getInstance(context)

        if (database.existUser(item.id)) {
            database.deleteUser(item.id)

            val position = mList.indexOf(item)
            var removeAlsoIndexItem = false

            if (mList[position - 1] is SearchUserRowSection) {
                removeAlsoIndexItem = true

                // 바로 밑의 아이템이 Section이 아니라 다른 아이템이 들어있다면 Section을 삭제하지 않는다.
                if (mList.size - 1 >= position + 1 && mList[position + 1] !is SearchUserRowSection) {
                    removeAlsoIndexItem = false
                }
            }

            mList.remove(item)
            notifyItemRemoved(position)

            if (removeAlsoIndexItem) {
                mList.removeAt(position - 1)
                notifyItemRemoved(position - 1)
            }
        }
    }

    fun setList(list: List<SearchUserRowItemType>) {
        mList.clear()

        // 한글 > 영어 > 숫자 > 특문 순 정열
        Collections.sort(list, kotlin.Comparator { t, t2 ->
            OrderingByKorean.compare(t.name.uppercase(Locale.getDefault()),
                t2.name.uppercase(Locale.getDefault())
            )
        })

        mList.addAll(list)

        mSection = sortIndexes(list)
        mSection.entries.reversed().forEach {
            mList.add (it.value, SearchUserRowSection(it.key.toString()))
        }

        notifyDataSetChanged()
    }

    /**
     * Only uses in Remote Fragment
     */
    fun onClickFavoriteCheckbox(position: Int, context: Context) {
        val item: SearchUserRowRemoteItem = mList[position] as SearchUserRowRemoteItem
        val database = DatabaseService.getInstance(context)
        if (database.existUser(item.id)) {
            database.deleteUser(item.id)
            val new = SearchUserRowRemoteItem(item.id, item.name, item.thumbnailUrl, false)

            mList[position] = new
            notifyItemChanged(position)
        } else {
            database.insertUser(item.id, item.name, item.thumbnailUrl)
            val new = SearchUserRowRemoteItem(item.id, item.name, item.thumbnailUrl, true)
            mList[position] = new
            notifyItemChanged(position)
        }
    }

    fun refreshList(context: Context) {
        val database = DatabaseService.getInstance(context)
        for (i in 0 until mList.size) {

            if (mList[i] is SearchUserRowRemoteItem) {
                val row = mList[i] as SearchUserRowRemoteItem

                if ( !database.existUser(row.id) && row.favorite ) {
                    val newValue = SearchUserRowRemoteItem(row.id, row.name, row.thumbnailUrl,false)

                    mList[i] = newValue
                    notifyItemChanged(i)
                }
            }
        }
    }

    // 리스트의 인덱스를 추출한다.
    private fun sortIndexes(sortedList: List<SearchUserRowItemType>): MutableMap<Char, Int> {

        val indexMap = mutableMapOf<Char, Int>()

        if (sortedList.isNotEmpty()) {
            val initChar = sortedList.first().name[0].uppercaseChar()
            val initCharIndex = if (CharUtil.isKorean(initChar)) KoreanCharacterUtil.getCompatChoseong(initChar) else initChar

            indexMap[initCharIndex] = 0

            for (i in 0 until sortedList.size - 1) {
                val currentChar = sortedList[i].name[0].uppercaseChar()
                val nextChar = sortedList[i + 1].name[0].uppercaseChar()
                val currentCharIndex = if (CharUtil.isKorean( currentChar ) ) KoreanCharacterUtil.getCompatChoseong(currentChar) else currentChar
                val nextCharIndex = if (CharUtil.isKorean( nextChar ) ) KoreanCharacterUtil.getCompatChoseong(nextChar) else nextChar

                if (currentCharIndex != nextCharIndex) {
                    indexMap[nextCharIndex] = i + 1  // 섹션의 마지막 인덱스의 다음에 삽입 하기 때문에 +1
                }
            }
        }

        return indexMap
    }
}
