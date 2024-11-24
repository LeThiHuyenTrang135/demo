package com.example.baiktra;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;
    private Button btnSubmit;
    private TextView tvResult, tvScore;
    private CheckBox cbSaveScore;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "QuizAppPrefs";
    private static final String SCORE_KEY = "quiz_score";

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvResult = findViewById(R.id.tvResult);
        tvScore = findViewById(R.id.tvScore);
        cbSaveScore = findViewById(R.id.cbSaveScore);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        score = sharedPreferences.getInt(SCORE_KEY, 0);
        tvScore.setText("Điểm: " + score);

        btnSubmit.setOnClickListener(v -> submitAnswer());
    }

    private void submitAnswer() {
        int selectedOption = rgOptions.getCheckedRadioButtonId();
        if (selectedOption == -1) {
            Toast.makeText(this, "Hãy chọn một câu trả lời!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isCorrect = false;
        if (selectedOption == R.id.rbOption1) { // Hà Nội là đúng
            isCorrect = true;
            score += 10;
        } else {
            score = Math.max(score - 5, 0);
        }

        // Chỉ lưu điểm vào SharedPreferences nếu checkbox được chọn
        if (cbSaveScore.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SCORE_KEY, score);
            editor.apply();
        } else {
            // Nếu không chọn lưu điểm, reset điểm về 0
            score = 0;
        }

        showResult(isCorrect);
    }

    private void showResult(boolean isCorrect) {
        if (isCorrect) {
            tvResult.setText("Câu trả lời đúng!");
            tvResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            tvResult.setText("Câu trả lời sai!");
            tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        tvScore.setText("Điểm: " + score); // Cập nhật điểm
    }
}
