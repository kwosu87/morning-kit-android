package com.yooiistudios.morningkit.common.unlock;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yooiistudios.morningkit.R;
import com.yooiistudios.morningkit.setting.store.iab.SKIabProducts;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by StevenKim in Morning Kit from Yooii Studios Co., LTD. on 2014. 1. 27.
 *
 * MNUnlockListAdapter
 */
public class MNUnlockListAdapter extends BaseAdapter {

    private Context context;
    private MNUnlockOnClickListener onClickListener;
    private String productSku;

    private MNUnlockListAdapter(){}
    protected MNUnlockListAdapter(Context context, MNUnlockOnClickListener onClickListener, String productSku) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.productSku = productSku;
    }

    @Override
    public int getCount() {
        if (productSku.equals(SKIabProducts.SKU_CAT)) {
            return 3;
        }
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.unlock_listview_item, parent, false);
        if (convertView != null) {
            MNUnlockListViewItemViewHolder viewHolder = new MNUnlockListViewItemViewHolder(convertView);
            boolean isCellUsed = false;

            List<String> ownedSkus = SKIabProducts.loadOwnedIabProducts(context);

            int convertedIndex = position;
            if (productSku.equals(SKIabProducts.SKU_CAT) && position > 0) {
                convertedIndex++;
            }

            // 사용 되었다면 폰트색은 a8a8a8, 아니라면 white
            switch (convertedIndex) {
                case 0:
                    isCellUsed = ownedSkus.contains(SKIabProducts.SKU_FULL_VERSION);
                    if (isCellUsed) {
                        viewHolder.getDescriptionTextView().setText(R.string.unlock_everything);
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_fullversion_2_99_icon_off);
                    } else {
                        setPointColoredTextView(viewHolder.getDescriptionTextView(),
                                context.getString(R.string.unlock_everything),
                                context.getString(R.string.unlock_everything_highlight));
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_fullversion_2_99_icon_on);
                    }
                    break;

                case 1:
                    isCellUsed = ownedSkus.contains(productSku);
                    viewHolder.getDescriptionTextView().setText(R.string.unlock_only_this);
                    if (isCellUsed) {
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_buyit_icon_off);
                    } else {
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_buyit_icon_on);
                    }
                    break;

                case 2:
                    // 구매를 했다면 사용할 필요가 없고, 구매를 하지 않았다면 리뷰 아이템을 클릭했는지를 체크
                    if (ownedSkus.contains(productSku) || ownedSkus.contains(SKIabProducts.SKU_FULL_VERSION)) {
                        isCellUsed = true;
                    } else {
                        isCellUsed = context.getSharedPreferences(MNUnlockActivity.SHARED_PREFS, Context.MODE_PRIVATE)
                                .getBoolean(MNUnlockActivity.REVIEW_USED, false);
                    }

                    if (isCellUsed) {
                        viewHolder.getDescriptionTextView().setText(R.string.unlock_review);
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_rating_icon_off);
                    } else {
                        setPointColoredTextView(viewHolder.getDescriptionTextView(),
                                context.getString(R.string.unlock_review),
                                context.getString(R.string.unlock_review_highlight));
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_rating_icon_on);
                    }
                    break;
                case 3:
                    // 구매를 했다면 사용할 필요가 없고, 구매를 하지 않았다면 리뷰 아이템을 클릭했는지를 체크
                    if (ownedSkus.contains(productSku) || ownedSkus.contains(SKIabProducts.SKU_FULL_VERSION)) {
                        isCellUsed = true;
                    } else {
                        isCellUsed = context.getSharedPreferences(MNUnlockActivity.SHARED_PREFS, Context.MODE_PRIVATE)
                                .getBoolean(MNUnlockActivity.RECOMMEND_USED, false);
                    }

                    // Facebook 공유 전용 옵션으로 만들 것이기에 임시로 Facebook을 영어로 붙임
                    String description = "Facebook : " + context.getString(R.string.unlock_recommend);
                    if (isCellUsed) {
                        viewHolder.getDescriptionTextView().setText(description);
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_recommend_icon_off);
                    } else {
                        setPointColoredTextView(viewHolder.getDescriptionTextView(), description,
                                context.getString(R.string.unlock_recommend_highlight));
                        viewHolder.getIconImageView().setImageResource(R.drawable.unlock_recommend_icon_on);
                    }
                    break;

            }
            viewHolder.getOuterLayout().setBackgroundResource(R.color.classic_gray_forward_normal_color);

            if (isCellUsed) {
                viewHolder.getDescriptionTextView().setTextColor(Color.parseColor("#a8a8a8"));
                viewHolder.getInnerLayout().setBackgroundResource(
                        R.drawable.shape_rounded_view_classic_gray_unlock_used);
                viewHolder.getInnerLayout().setClickable(false);
                viewHolder.getInnerLayout().setFocusable(false);
                viewHolder.getInnerLayout().setOnClickListener(null);
            } else {
                viewHolder.getInnerLayout().setBackgroundResource(
                        R.drawable.shape_rounded_view_classic_gray_unlock_default);
                viewHolder.getInnerLayout().setClickable(true);
                viewHolder.getInnerLayout().setFocusable(true);
                viewHolder.getInnerLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onItemClick(position);
                    }
                });
            }
        }
        return convertView;
    }

    private void setPointColoredTextView(TextView textView, String descriptionString, String pointedString) {
        if (textView != null) {
            SpannableString spannableString = new SpannableString(descriptionString);

            int pointedStringIndex = descriptionString.indexOf(pointedString);
            if (pointedStringIndex != -1) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00ccff")),
                        pointedStringIndex, pointedStringIndex + pointedString.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);
            }
        } else {
            throw new AssertionError("TextView shouldn't be null");
        }
    }

    /**
     * ViewHolder
     */
    static class MNUnlockListViewItemViewHolder {
        @Getter @InjectView(R.id.unlock_listview_item_outer_layout)         RelativeLayout outerLayout;
        @Getter @InjectView(R.id.unlock_listview_item_inner_layout)         RelativeLayout innerLayout;
        @Getter @InjectView(R.id.unlock_listview_item_imageview)            ImageView iconImageView;
        @Getter @InjectView(R.id.unlock_listview_item_description_textview) TextView descriptionTextView;

        public MNUnlockListViewItemViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
