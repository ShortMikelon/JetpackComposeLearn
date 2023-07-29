package kz.asetkenes.learnandroid.data.tags.room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class TestDataCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Выполните операции вставки данных здесь
        db.execSQL("INSERT INTO `tags` (`name`) VALUES " +
                "('android'), " +
                "('web'), " +
                "('Java'), " +
                "('Kotlin'), " +
                "('HTML'), " +
                "('CSS'), " +
                "('JavaScript'), " +
                "('PHP'), " +
                "('C#'), " +
                "('desktop'), " +
                "('HTTP'), " +
                "('C++');")

        db.execSQL("INSERT INTO `users` (`name`, `email`, `password`, `about_me`, `date_birthday`, `created_at`) VALUES " +
                "('Aset', 'aset@google.com', 'password', 'android developer', 0, 0), " +
                "('Erasyl', 'erasyl@google.com', 'password', 'react/doc.net developer', 0, 0), " +
                "('Rasul', 'rasul@google.com', 'password', 'java spring boot developer', 0, 0), " +
                "('Bekzat', 'beka@google.com', 'password', '??? developer', 0, 0), " +
                "('Ali', 'ali@google.com', 'password', 'flutter developer', 0, 0);")

        db.execSQL("INSERT INTO `articles` (`name`, `author_id`, `description`, `body`, `created_at`) VALUES " +
                "('Room', 1, 'Learn Room part 1', 'Learn Room part 1', 0), " +
                "('Room', 1, 'Learn Room part 2', 'Learn Room part 2', 0), " +
                "('Room', 1, 'Learn Room part 2', 'Learn Room part 2', 0), " +
                "('Kotlin', 1, 'Learn Kotlin part 1', 'Learn Kotlin part 1', 0), " +
                "('Kotlin', 1, 'Learn Kotlin part 2', 'Learn Kotlin part 2', 0), " +
                "('Kotlin', 1, 'Learn Kotlin part 2', 'Learn Kotlin part 2', 0), " +
                "('Java', 1, 'Learn Java part 1', 'Learn Java part 1', 0), " +
                "('Java', 1, 'Learn Java part 2', 'Learn Java part 2', 0), " +
                "('Java', 1, 'Learn Java part 2', 'Learn Java part 2', 0);")

        db.execSQL("INSERT INTO `article_tags` (`article_id`, `tag_id`) VALUES " +
                "(1, 1), " +
                "(2, 1), " +
                "(3, 1), " +
                "(4, 4), " +
                "(5, 4), " +
                "(5, 3), " +
                "(6, 1), " +
                "(6, 4), " +
                "(7, 3), " +
                "(8, 3), " +
                "(8, 1), " +
                "(9, 4), " +
                "(9, 3);")
    }
}