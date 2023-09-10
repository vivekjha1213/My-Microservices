package com.userService.Exception;

public class ResouceNotFoundException extends RuntimeException{

    public ResouceNotFoundException(){
        super("Resouce Not Found on the DataBase");
    }

    public ResouceNotFoundException(String message){
        super(message);
    }

}
