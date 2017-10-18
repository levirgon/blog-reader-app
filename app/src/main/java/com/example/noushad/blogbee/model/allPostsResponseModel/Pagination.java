package com.example.noushad.blogbee.model.allPostsResponseModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Pagination{

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("total")
	private int total;

	@SerializedName("count")
	private int count;

	//@SerializedName("links")
//	private Links links;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("current_page")
	private int currentPage;

	public int getPerPage(){
		return perPage;
	}

	public int getTotal(){
		return total;
	}

	public int getCount(){
		return count;
	}

//	public Links getLinks(){
//		return links;
//	}

	public int getTotalPages(){
		return totalPages;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"Pagination{" + 
			"per_page = '" + perPage + '\'' + 
			",total = '" + total + '\'' + 
			",count = '" + count + '\'' + 
		//	",links = '" + links + '\'' +
			",total_pages = '" + totalPages + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			"}";
		}
}