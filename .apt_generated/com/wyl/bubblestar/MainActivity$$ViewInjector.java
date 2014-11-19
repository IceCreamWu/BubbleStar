// Generated code from Butter Knife. Do not modify!
package com.wyl.bubblestar;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.wyl.bubblestar.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099649, "field 'connectBtn' and method 'connect'");
    target.connectBtn = (android.widget.Button) view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.connect();
        }
      });
    view = finder.findRequiredView(source, 2131099648, "field 'chatText'");
    target.chatText = (android.widget.TextView) view;
  }

  public static void reset(com.wyl.bubblestar.MainActivity target) {
    target.connectBtn = null;
    target.chatText = null;
  }
}
