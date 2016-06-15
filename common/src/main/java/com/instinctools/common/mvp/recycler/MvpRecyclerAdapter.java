package com.instinctools.common.mvp.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.instinctools.common.mvp.presenter.BaseModelPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MvpRecyclerAdapter<M, P extends BaseModelPresenter<M, ?>, VH extends MvpRecyclerHolder<P>> extends RecyclerView.Adapter<VH> {

    protected final List<M> models;

    public MvpRecyclerAdapter() {
        models = new ArrayList<>();
    }

    @NonNull
    protected P getPresenter(@NonNull M model) {
        // TODO: not any cache here now - will change in future
        return createPresenter(model);
    }

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
        holder.unbindPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        holder.unbindPresenter();
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindPresenter(getPresenter(getItem(position)));
    }

    @NonNull
    protected abstract P createPresenter(@NonNull M model);

    @NonNull
    protected abstract Object getModelId(@NonNull M model);

    public void clearAndAddAll(Collection<M> data) {
        models.clear();

        for (M item : data) {
            addInternal(item);
        }

        notifyDataSetChanged();
    }

    public void addAll(Collection<M> data) {
        for (M item : data) {
            addInternal(item);
        }

        int addedSize = data.size();
        int oldSize = models.size() - addedSize;
        notifyItemRangeInserted(oldSize, addedSize);
    }

    public void addItem(M item) {
        addInternal(item);
        notifyItemInserted(models.size());
    }

    public void updateItem(M item) {
        Object modelId = getModelId(item);

        // Swap the model
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(position);
            models.add(position, item);
        }

        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    public void removeItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(item);
        }

        if (position >= 0) {
            notifyItemRemoved(position);
        }
    }

    private int getItemPosition(M item) {
        Object modelId = getModelId(item);

        int position = -1;
        for (int i = 0; i < models.size(); i++) {
            M model = models.get(i);
            if (getModelId(model).equals(modelId)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void addInternal(M item) {
        models.add(item);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @NonNull
    protected M getItem(int position) {
        return models.get(position);
    }
}
