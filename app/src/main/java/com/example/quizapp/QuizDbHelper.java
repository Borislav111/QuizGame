package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quizapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuestionsTable() {
        Question q1 = new Question("2 + 2 = ?",
                "22", "4", "5", "222", 2, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("През коя година е създадена България?",
                "630", "891", "681", "1861", 3, Question.DIFFICULTY_EASY);
        addQuestion(q2);
        Question q3 = new Question("Колко тежи възрастния син кит?",
                "1500 - 5000кг.", "10 000 - 13 000кг.", "200 000 - 250 000кг.", "50 000 - 150 000кг.", 4, Question.DIFFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Коя е най-голямата страна по площ?",
                "Турция", "САЩ", "Русия", "Китай", 3, Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("Колко години е един век?",
                "100", "10", "1000", "10 000", 1, Question.DIFFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("Кой български град се счита за столицата на Розовата долина?",
                "Сопот", "Казанлък", "Карлово","Калофер", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q6);
        Question q7 = new Question("Кой е най–южно разположеният български град?",
                "Златоград", "Мадан", "Смолян","Чепеларе", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q7);
        Question q8 = new Question("В близост до кой български град е разположена местността „Узана“," +
                " където се намира географският център на България ?",
                "Габрово", "Севлиево", "Троян","Велико Търново", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q8);
        Question q9 = new Question("В кой български град се намира „Алеята на космонавтите“?",
                "Лясковец", "Ловеч", "Луковит","Лом", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q9);
        Question q10 = new Question("Коя е най–новата община в България, която е обявена през 2015г.?",
                "Сърница", "Летница", "Белица","Златица", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q10);
        Question q11 = new Question("Колко метра е висок връх Нанга Парбат?",
                "789м.", "1269м.", "8126м.","4412м.", 3, Question.DIFFICULTY_HARD);
        addQuestion(q11);
        Question q12 = new Question("Приблизително колко милиона е населението на Стокхолм, Швеция?",
                "5", "6", "3","2", 4, Question.DIFFICULTY_HARD);
        addQuestion(q12);
        Question q13 = new Question("Кой е създал първия автомобил?",
                "Николас Отто", "Карл Бенц", "Хенри Форд","Феликс Ванкел", 2, Question.DIFFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("Какво липсва на белия шоколад, в сравнение с кафявия?",
                "Захар", "Какаово мляко", "Какаова маса","Какаово масло", 3, Question.DIFFICULTY_HARD);
        addQuestion(q14);
        Question q15 = new Question("Кой град е столица на Бразилия?",
                "Рио Де Жанейро", "Бразилия", "Канкун","Буенос Айрес", 2, Question.DIFFICULTY_HARD);
        addQuestion(q15);
    }
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}