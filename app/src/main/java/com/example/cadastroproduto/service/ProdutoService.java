package com.example.cadastroproduto.service;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.cadastroproduto.MyApp;
import com.example.cadastroproduto.R;
import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.utils.ConfigSharedPreferences;
import com.example.cadastroproduto.utils.DateUtil;
import com.example.cadastroproduto.utils.HttpHelper;
import com.example.cadastroproduto.utils.NetworkType;

import static com.example.cadastroproduto.utils.ConfigSharedPreferences.*;

public class ProdutoService {

    public static List<Produto> getProdutos(boolean isForcarAtualizacao) throws IOException {
        List<Produto> produtos = null;
        boolean bGet = false;
        String json = "";
        String mensagem2 = "";
        String networkType = "WIFI";
//        String networkType = NetworkType.getNetworkClass(MyApp.getContext()); está ocorrendo problema resolver depois


        String sServidorIP = getString(MyApp.getContext(), "cfgServidorIP");//é possível chamar getString pq é static
        // Log.w("DDM - Log Sandro", "sServidorIP = " + sServidorIP);

        if (networkType.equals("-") || !networkType.equals("WIFI")) {
            String mensagem = (networkType.equals("-") ? "Sem conexão com a Internet." : "Conexão " + networkType);

            if (sServidorIP != null && !sServidorIP.isEmpty())
                mensagem2 = MyApp.getContext().getResources().getString(R.string.produtoscarregadosdopropriocelular);

            Toast.makeText(MyApp.getContext(), mensagem + "\n" + mensagem2, Toast.LENGTH_LONG).show();
            isForcarAtualizacao = false;
        }
        //A lógica acima é simples, primeiro verifica se tem conexão com a internet, e retorna a mesagem sem conexão com a internet.
        //Depois verifica se o servidor ip foi configurado, se não tiver é retornado a mensagem de produtos carregados do próprio celular.
        //por fim como não tem conexão com o servidor forçar atualização fica false.



        if (isForcarAtualizacao && sServidorIP != null && !sServidorIP.isEmpty()) {
            HttpHelper helper = new HttpHelper();
            json = helper.doGet("http://" + sServidorIP + ":8080/produtos");
            bGet = true;
        } else {
            json = getJsonConfiguracao();
        }

        if (json == null || json.isEmpty()) {
            if (sServidorIP == null || sServidorIP.isEmpty()) {
                throw new IOException("IP do servidor não configurado!\nNenhum produto cadastrado no celular.");
            } else {
                throw new IOException("Nenhum produto cadastrado no celular.");
            }
        } else {
            produtos = parserJSON(json);

            if (produtos == null || produtos.isEmpty()) {
                produtos = getListaProdutosConfiguracao();
            }
            else {
                if (bGet) {
                    setString(MyApp.getContext(), "cfgJsonProdutos", json);
                    setString(MyApp.getContext(), "cfgDtUltAtz", DateUtil.DataDMY());
                }
            }
        }

        return produtos;
    }

    private static String getJsonConfiguracao() {
        String json = getString(MyApp.getContext(), "cfgJsonProdutos");
        return json;
    }

    public static List<Produto> getListaProdutosConfiguracao() throws IOException {
        List<Produto> produtos = null;

        String json = getJsonConfiguracao();

        if (json == null || json.isEmpty()) {
            throw new IOException("Nenhum produto cadastrado no celular.");
        }

        produtos = parserJSON(json);

        return produtos;
    }

    private static List<Produto> parserJSON(String json) {
        List<Produto> produtos = new ArrayList<>();
        ///JSONObject converte string em json
        try {
            JSONArray produtosA = new JSONArray(json);



            for (int i = 0; i < produtosA.length(); i++) {
                JSONObject jsonObj = produtosA.getJSONObject(i);
                Produto p = new Produto();

//                p.setEan(jsonProduto.optString("ean"));

                p.setNome(jsonObj.optString("nome"));
                p.setPreco(jsonObj.optDouble("valor"));

                produtos.add(p);
            }
        } catch (JSONException e) {
            Log.i("Daniel", e.getMessage());
            produtos.clear();
        }

        return produtos;
    }
}
