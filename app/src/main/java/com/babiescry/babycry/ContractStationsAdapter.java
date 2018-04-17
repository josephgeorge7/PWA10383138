package com.babiescry.babycry;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

public class ContractStationsAdapter extends RecyclerView.Adapter<ContractsStationViewHolder> {

    private JSONArray jsonArray = null;
    private String endpoint = MainActivity.CONTRACTS_ENDPOINT;

    @Override
    public ContractsStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contractView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contract_item, parent, false);
        return new ContractsStationViewHolder(contractView);
    }

    @Override
    public void onBindViewHolder(ContractsStationViewHolder holder, int position) {
        try {
            holder.setData(jsonArray.getJSONObject(position), endpoint);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray!=null?jsonArray.length():0;
    }

    public void updateDataSet(JSONArray jsonArray, String endpoint) {
        this.jsonArray = jsonArray;
        this.endpoint = endpoint;
        notifyDataSetChanged();
    }
}
