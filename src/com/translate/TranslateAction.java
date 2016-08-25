package com.translate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import org.apache.http.util.TextUtils;

import java.awt.*;

/**
 * 作者: guofeng
 * 日期: 16/8/25.
 */
public class TranslateAction extends AnAction {

    private static String searchUrl = "http://fanyi.youdao.com/openapi.do?keyfrom=Skykai521&key=977124034&type=data&doctype=json&version=1.1&q=";


    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        final Editor mEditor = e.getData(PlatformDataKeys.EDITOR);
        if (mEditor == null) return;
        SelectionModel model = mEditor.getSelectionModel();
        String selectText = model.getSelectedText();
        if (TextUtils.isEmpty(selectText)) return;
        ThreadPools.executor(new Runnable() {
            @Override
            public void run() {
                HttpUtils.httpGet(searchUrl + selectText, new HttpUtils.ResponseCallBack() {

                    @Override
                    public void onResponseComplete(ExplainBean result) {
                        if (result.errorCode == 0) {
                            String explain = result.translation.toString() + "\n" + result.basic.explains;
                            showPopupDialog(mEditor, explain);
                        }
                    }
                    @Override
                    public void onResponseError(String error) {

                    }
                });
            }
        });
    }

    private void showPopupDialog(final Editor editor, final String result) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                JBPopupFactory factory = JBPopupFactory.getInstance();
                factory.createHtmlTextBalloonBuilder(result,
                        null, new JBColor(new Color(186, 238, 186),
                                new Color(73, 117, 73)), null)
                        .setFadeoutTime(5000)
                        .createBalloon()
                        .show(factory.guessBestPopupLocation(editor),
                                Balloon.Position.below);
            }
        });
    }
}
