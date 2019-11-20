//package com.example.cadastroproduto.adapters;
//
//import android.media.Image;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.cadastroproduto.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LineAdapter extends RecyclerView.Adapter<LineAdapter.LineViewHolder>{
//
//    private final List<Image> images;
//
//    public LineAdapter(ArrayList images) {
//        this.images = images;
//    }
//
//    @NonNull
//    @Override
//    public LineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return new LineViewHolder(LayoutInflater
//                .from(viewGroup.getContext())
//                .inflate(R.layout.main_line_view, viewGroup, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull LineViewHolder lineViewHolder, final int position) {
//        lineViewHolder.title.setText(String.format(Locale.getDefault(), "%s, %d - %s",
//                mUsers.get(position).getName(),
//                mUsers.get(position).getAge(),
//                mUsers.get(position).getCity()
//        ));
//
//        lineViewHolder.moreButton.setOnClickListener(view -> updateItem(position));
//        lineViewHolder.deleteButton.setOnClickListener(view -> removerItem(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mUsers != null ? mUsers.size() : 0;
//    }
//    /**
//     * Método publico chamado para atualziar a lista.
//     * @param user Novo objeto que será incluido na lista.
//     */
//    public void updateList(UserModel user) {
//        insertItem(user);
//    }
//
//    // Método responsável por inserir um novo usuário na lista
//    //e notificar que há novos itens.
//    private void insertItem(UserModel user) {
//        mUsers.add(user);
//        notifyItemInserted(getItemCount());
//    }
//
//    // Método responsável por atualizar um usuário já existente na lista.
//    public void updateItem(int position){
//        UserModel userModel = mUsers.get(position);
//        userModel.incrementeAge();
//        notifyItemChanged(position);
//    }
//
//    // Método responsável por remover um usuário da lista.
//    public void removerItem(int position){
//        UserModel userModel = mUsers.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mUsers.size());
//    }
//
//    public class LineViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView title;
//        public ImageButton moreButton;
//        public ImageButton deleteButton;
//
//        public LineViewHolder(@NonNull View itemView) {
//            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.main_line_title);
//            moreButton = (ImageButton) itemView.findViewById(R.id.main_line_more);
//            deleteButton = (ImageButton) itemView.findViewById(R.id.main_line_delete);
//        }
//    }
//
//}
