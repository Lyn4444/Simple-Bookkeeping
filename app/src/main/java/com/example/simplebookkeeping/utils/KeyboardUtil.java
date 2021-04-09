package com.example.simplebookkeeping.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.simplebookkeeping.R;

public class KeyboardUtil {

    private final KeyboardView keyboardView;
    private final EditText editText;

    public KeyboardUtil(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);
        Keyboard keyboard = new Keyboard(this.editText.getContext(), R.xml.keyboard);
        this.keyboardView.setKeyboard(keyboard);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }

    public void showKeyboard() {
        int isDisplay = keyboardView.getVisibility();
        if (isDisplay == View.INVISIBLE || isDisplay == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int isDisplay = keyboardView.getVisibility();
        if (isDisplay == View.VISIBLE || isDisplay == View.INVISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }

    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_DONE:
//                    点击确定，调用接口回调方法
                    onEnsureListener.onEnsure();
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    editable.clear();
                    break;
                default:
                    editable.insert(start, Character.toString((char) primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };

    public interface OnEnsureListener {
        public void onEnsure();
    }

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

}
