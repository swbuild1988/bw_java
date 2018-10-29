package com.bandweaver.tunnel.common.biz.pojo;

import java.util.Collections;
import java.util.List;

public class ListPageUtil<T> {
    /**原集合*/
    private List<T> data;
 
    /** 上一页 */
    private int lastPage;

    /** 当前页 */
    private int pageNum;

    /** 下一页 */
    private int nextPage;

    /** 每页条数 */
    private int pageSize;

    /** 总页数 */
    private int totalPage;

    /** 总数据条数 */
    private int total;
    
    public ListPageUtil(List<T> data,int pageNum,int pageSize) {
        if (data == null) {
            throw new IllegalArgumentException("data must be not empty!");
        }
 
        this.data = data;
        this.pageSize = pageSize;
        /*this.totalPage = data.size()/pageSize;
        if(data.size()%pageSize!=0){
            this.totalPage++;
        }*/
        
        this.pageNum = pageNum;
        this.total = data.size();
        this.totalPage = (total + pageSize - 1) / pageSize;
        this.lastPage = pageNum-1>1? pageNum-1:1;
        this.nextPage = pageNum>=totalPage? totalPage: pageNum + 1;
        
    }
 
    /**
     * 得到分页后的数据
     *
     * @param pageNum 页码
     * @return 分页后结果
     */
    public List<T> getPagedList() {
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();//空数组
        }
        if(fromIndex<0){
            return Collections.emptyList();//空数组
        }
        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

	public int getLastPage() {
		return lastPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotal() {
		return total;
	}
 
    
}
