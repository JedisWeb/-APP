package cn.edu.hbuas.sl.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.Feedback;
import cn.edu.hbuas.sl.bean.utils.FeedbackDaoUtils;

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.feedback_layout_back_img)
    ImageView feedbackLayoutbackImg;
    @BindView(R.id.feedback_layout_title_textView)
    TextView feedbackLayoutTitleTextView;
    @BindView(R.id.feedback_layout_submit_textView)
    TextView feedbackLayoutSubmitTextView;
    @BindView(R.id.feedback_layout_head_relativeLayout)
    RelativeLayout feedbackLayoutHeadRelativeLayout;
    @BindView(R.id.feedback_layout_feedback_editText)
    EditText feedbackLayoutFeedbackEditText;
    @BindView(R.id.feedback_layout_tel_editText)
    EditText feedbackLayoutTelEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.feedback_layout_back_img,
            R.id.feedback_layout_title_textView,
            R.id.feedback_layout_submit_textView,
            R.id.feedback_layout_head_relativeLayout,
            R.id.feedback_layout_feedback_editText,
            R.id.feedback_layout_tel_editText
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.feedback_layout_back_img: {
                finish();
            }
            break;
            case R.id.feedback_layout_title_textView:
                break;
            case R.id.feedback_layout_submit_textView: {
                String content = feedbackLayoutFeedbackEditText.getText().toString().trim();
                String tel = feedbackLayoutTelEditText.getText().toString().trim();
                if (StringUtils.isNotEmpty(content)) {
                    Feedback feedback = new Feedback();
                    feedback.setContent(content);
                    feedback.setTel(tel);
                    FeedbackDaoUtils.insertData(FeedbackActivity.this, feedback);
                    Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            break;
            case R.id.feedback_layout_head_relativeLayout:
                break;
            case R.id.feedback_layout_feedback_editText:
                break;
            case R.id.feedback_layout_tel_editText:
                break;
        }
    }

}
