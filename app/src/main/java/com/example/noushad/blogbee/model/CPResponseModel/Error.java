package com.example.noushad.blogbee.model.CPResponseModel;

import java.util.List;

public class Error{
	private List<String> description;
	private List<String> coverImage;
	private List<String> title;

	public void setDescription(List<String> description){
		this.description = description;
	}

	public List<String> getDescription(){
		return description;
	}

	public void setCoverImage(List<String> coverImage){
		this.coverImage = coverImage;
	}

	public List<String> getCoverImage(){
		return coverImage;
	}

	public void setTitle(List<String> title){
		this.title = title;
	}

	public List<String> getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"Error{" + 
			"description = '" + description + '\'' + 
			",cover_image = '" + coverImage + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}