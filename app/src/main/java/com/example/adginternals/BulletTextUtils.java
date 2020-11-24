package com.example.adginternals;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;

public class BulletTextUtils {
    public static CharSequence makeBulletListFromStringArrayResource(int leadingMargin, Context context, int stringArrayResId) {
        return makeBulletList(leadingMargin, context.getResources().getStringArray(stringArrayResId));
    }
    public static CharSequence makeBulletListFromStringResources(int leadingMargin, Context context, int... linesResIds) {
        int len = linesResIds.length;
        CharSequence[] cslines = new CharSequence[len];
        for (int i = 0; i < len; i++) {
            cslines[i] = context.getString(linesResIds[i]);
        }
        return makeBulletList(leadingMargin, cslines);
    }
    public static CharSequence makeBulletList(int leadingMargin, CharSequence... lines) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (int i = 0; i < lines.length; i++) {
            CharSequence line = lines[i] + (i < lines.length-1 ? "\n" : "");
            Spannable spannable = new SpannableString(line);
            spannable.setSpan(new BulletSpan(leadingMargin), 0, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            sb.append(spannable);
        }
        return sb;
    }
}
