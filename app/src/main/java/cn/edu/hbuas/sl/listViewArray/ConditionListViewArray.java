package cn.edu.hbuas.sl.listViewArray;

import android.widget.EditText;

public class ConditionListViewArray {
    private String num;
    private EditText questionEditText;
    private EditText answerEditText;

    public ConditionListViewArray() {
    }

    public ConditionListViewArray(String num, EditText questionEditText, EditText answerEditText) {
        this.num = num;
        this.questionEditText = questionEditText;
        this.answerEditText = answerEditText;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public EditText getQuestionEditText() {
        return questionEditText;
    }

    public void setQuestionEditText(EditText questionEditText) {
        this.questionEditText = questionEditText;
    }

    public EditText getAnswerEditText() {
        return answerEditText;
    }

    public void setAnswerEditText(EditText answerEditText) {
        this.answerEditText = answerEditText;
    }
}
