package com.szqd.framework.model;

import java.util.List;

/**
 * 分页用的值对象
 * 
 * @author alan.li
 */
public class Pager<E>
{
	private Object param = null;
	/**
	 * 总记录数
	 */
	private int totalRows = 0;

	/**
	 * 每页记录数
	 */
	private int capacity = 15;

	/**
	 *  当前页
	 */
	private int pageNo = 1;
	


	/**
	 *该页的分页记录集
	 */
	private List<E> value;
	
	public Pager()
	{}
	
	public Pager(int capacity)
	{
		setCapacity(capacity);
	}

	/**
	 * 取得总记录数
	 */
	public int getTotalRows()
	{
		return totalRows;
	}

	/**
	 * 设置总记录数
	 */
	public void setTotalRows(int totalRows)
	{
		if (totalRows >= 0) this.totalRows = totalRows;
	}

	/**
	 * 取得每页记录数
	 */
	public int getCapacity()
	{
		return this.capacity;
	}

	/**
	 * 设置每页记录数
	 */
	public void setCapacity(int capacity)
	{
		if (capacity > 0) this.capacity = capacity;
	}

	/**
	 *  取得当前页码，默认值为1
	 */
	public int getPageNo()
	{
//		int totalPages = getTotalPages();
//		if (pageNo > totalPages) return totalPages;
//		else return pageNo;
		return pageNo;
	}


	/**
	 * 当当前页码大于0时，设置当前页码
	 */
	public void setPageNo(int pageNo)
	{
		if (pageNo > 0) this.pageNo = pageNo;
	}

	/**
	 * 取得记录的偏移量，从0开始，如果每页记录数为10，则第一页偏移量为0，第二页，偏移量则为10
	 */
	public int getOffset()
	{
//		int totalPages = getTotalPages();
//		if (pageNo > totalPages) return (totalPages - 1) * capacity; //当前页码大于最大页码数，使用最大页码数进行计算
//		else if (pageNo > 0) return (pageNo - 1) * capacity; //当前页码在正常范围内（小于最大页码数同时大于0）
//		else return 0;
		return (pageNo - 1) * capacity;
	}

	/**
	 * 根据总记录数以及每页记录数，计算总页数，默认总页数为1
	 */
	public int getTotalPages()
	{
		int totalPages = (int) Math.ceil((double) (totalRows) / (double) capacity);
		if (totalPages > 0) return totalPages;
		else return 0;
	}

	/**
	 * 取得该次分页的值
	 */
	public List<E> getValue()
	{
		return value;
	}

	/**
	 *  设置该次分页的值
	 */
	public void setValue(List<E> value)
	{
		this.value = value;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

}
