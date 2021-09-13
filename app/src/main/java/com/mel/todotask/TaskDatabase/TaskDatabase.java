package com.mel.todotask.TaskDatabase;

import android.provider.BaseColumns;

public class TaskDatabase {

    public static final String db_name = "Task.db";
    public static final int  db_version = 1;

    public class TaskTable implements BaseColumns{
        public static final String table_name = "Tasks";
        public static final String COl = "Task_Detail";
    }
}
