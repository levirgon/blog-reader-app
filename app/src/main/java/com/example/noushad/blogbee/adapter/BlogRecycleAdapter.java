package com.example.noushad.blogbee.adapter;

import android.content.Context;
import android.media.Image;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.fragment.ListFragment;
import com.example.noushad.blogbee.model.allPostsResponseModel.DataItem;
import com.example.noushad.blogbee.utils.PaginationAdapterCallback;
import com.example.noushad.blogbee.utils.WebOperations;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by noushad on 7/16/17.
 */

public class BlogRecycleAdapter extends RecyclerView.Adapter {

    private final List<DataItem> mItems;
    private ListFragment.OnItemSelectedInterface mListener;
    private Context mContext;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private PaginationAdapterCallback mCallback;
    private DataItem mDataItem;
    private Context parentContext;

    private String errorMsg;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    public BlogRecycleAdapter(Context context, ListFragment.OnItemSelectedInterface listener,
                              PaginationAdapterCallback callback) {

        mContext = context;
        mItems = new ArrayList<>();
        mListener = listener;
        this.mCallback = callback;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        parentContext = parent.getContext();
        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.list_item, parent, false);
                viewHolder = new BlogVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DataItem dataItem = mItems.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                BlogVH blogVH = (BlogVH) holder;
                try {
                    blogVH.bind(dataItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    "An unexpected error occurred!");

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    private class BlogVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected Context context;
        private ImageView mCoverImage;
        private ImageView mProfileImage;
        private TextView mLastUpdatedTextView;
        private TextView mCommentCountTextView;
        private TextView mNameTextView;
        private TextView mTitleTextView;
        private ImageView mImageSettingButton;


        public BlogVH(View itemView) {
            super(itemView);
            mCommentCountTextView = (TextView) itemView.findViewById(R.id.commentCountTextView);
            mCoverImage = (ImageView) itemView.findViewById(R.id.blogCoverImageView);
            mProfileImage = (ImageView) itemView.findViewById(R.id.user_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.blogerNameTextView);
            mLastUpdatedTextView = (TextView) itemView.findViewById(R.id.timeCountTextView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.blogTopicTextView);
            mImageSettingButton = (ImageView) itemView.findViewById(R.id.popupImageView);

            itemView.setOnClickListener(this);
        }

        private void bind(DataItem dataItem) throws ParseException {
            mDataItem = dataItem;
            String str_date=mDataItem.getLastChange().toString();
            DateFormat formatter ;
            Date date ;
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = (Date) formatter.parse(str_date);
            long longDate=date.getTime();

            mLastUpdatedTextView.setText(getlongtoago(longDate));
            WebOperations.loadImage(parentContext, mCoverImage, mDataItem.getCoverPhoto());
            if (mDataItem.hasCreatorInfo()) {
                if (WebOperations.hasValidPath(mDataItem.getUserInfo().getCoverPhoto()))
                    WebOperations.loadImage(parentContext, mProfileImage, mDataItem.getUserInfo().getCoverPhoto());
                mNameTextView.setText((mDataItem.getUserInfo()).getName());
            }
            mTitleTextView.setText(mDataItem.getTitle());
            mCommentCountTextView.setText(String.valueOf(mDataItem.getCommentCount()));
            mImageSettingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(mImageSettingButton,getPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            int id = mItems.get(pos).getId();
            mListener.onListBlogSelected(id);
        }
        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
            popup.show();
        }
    }


    private class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        private LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mItems.size() - 1);
        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void add(DataItem item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(List<DataItem> items) {
        for (DataItem item : items) {
            add(item);
        }
    }

    public void remove(DataItem item) {
        int position = mItems.indexOf(item);
        if (position > -1) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }

    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(mItems.get(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new DataItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = mItems.size() - 1;
        DataItem dataItem = mItems.get(position);

        if (dataItem != null) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }
    public static String getlongtoago(long createdAt) {
        //DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        Date date = null;
        date = new Date(createdAt);
        String crdate1 = dateFormatNeeded.format(date);

        // Date Calculation
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        crdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currenttime = dateFormat.format(cal.getTime());

        Date CreatedAt = null;
        Date current = null;
        try {
            CreatedAt = dateFormat.parse(crdate1);
            current = dateFormat.parse(currenttime);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = current.getTime() - CreatedAt.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " day ago ";
            } else {
                time = diffDays + " days ago ";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " hr ago";
                } else {
                    time = diffHours + " hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " min ago";
                    } else {
                        time = diffMinutes + " mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds + " secs ago";
                    }
                }

            }

        }
        return time;
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.popup_bookmark:
                    Toast.makeText(parentContext, "Bookmark will work from here soon", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.popup_edit:
                    Toast.makeText(parentContext, "Edit will work from here soon", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.popup_delete:
                    Toast.makeText(parentContext, "Delete will work from here soon", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.popup_like:
                    Toast.makeText(parentContext, "Like will work from here soon", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
