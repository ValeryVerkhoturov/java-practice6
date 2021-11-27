package com.company.objects;

import java.util.Date;

public record Task(String description, Date start, Date finish, int price){}
