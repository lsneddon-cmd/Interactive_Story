package io.lewiscodes.interacticestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Stack;

import io.lewiscodes.interacticestory.R;
import io.lewiscodes.interacticestory.model.Page;
import io.lewiscodes.interacticestory.model.Story;

public class StoryActivity extends AppCompatActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story story;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;
    private String name;
    private Stack<Integer> pageStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.d(TAG, name);

        story = new Story();
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);

        final Page page = story.getPage(pageNumber);

        storyImageView.setImageDrawable(ContextCompat.getDrawable(this, page.getImageId()));

        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        if (page.isFinalPage()) {
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText("Play Again");
            choice2Button.setOnClickListener((v) -> {
                pageStack.clear();
               loadPage(0);
            });
        } else {
            loadButtons(page);
        }
    }

    private void loadButtons(Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener((v) -> {
            int nextPage = page.getChoice1().getNextPage();
            loadPage(nextPage);
        });

        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener((v) -> {
            int nextPage = page.getChoice2().getNextPage();
            loadPage(nextPage);
        });
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()) {
            super.onBackPressed();
        } else {
            loadPage(pageStack.pop());
        }
    }

}
