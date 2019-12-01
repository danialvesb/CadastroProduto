package com.example.cadastroproduto.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cadastroproduto.MyApp;
import com.example.cadastroproduto.R;
import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.utils.ConfigSharedPreferences;
import com.example.cadastroproduto.utils.DateUtil;
import com.example.cadastroproduto.utils.HttpHelper;
import com.example.cadastroproduto.utils.IOUtils;
import com.example.cadastroproduto.utils.NetworkType;

import static com.example.cadastroproduto.utils.ConfigSharedPreferences.*;

public class ProdutoService {

    public static List<Produto> getProdutos(boolean isForcarAtualizacao) throws IOException {
        List<Produto> produtos = null;
        boolean bGet = false;
        String json = "";
        String mensagem2 = "";
        String networkType = "WIFI";


        String sServidorIP = getString(MyApp.getContext(), "cfgServidorIP");//é possível chamar getString pq é static
        String sServidorPorta = getString(MyApp.getContext(), "cfgServidorPorta");//é possível chamar getString pq é static

        if (isForcarAtualizacao && sServidorIP != null && !sServidorIP.isEmpty()) {
            HttpHelper helper = new HttpHelper();
            json = helper.doGet("http://" + sServidorIP +":"+sServidorPorta+ "/produtos");
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

            if (false) {
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

    public static Produto getProduto(Long id) throws IOException {
        Produto produto = null;
        boolean bGet = false;
        String json = "";
        String mensagem2 = "";

        String sServidorIP = getString(MyApp.getContext(), "cfgServidorIP");//é possível chamar getString pq é static
        String sServidorPorta = getString(MyApp.getContext(), "cfgServidorPorta");//é possível chamar getString pq é static


        HttpHelper helper = new HttpHelper();
        json = helper.doGet("http://" + sServidorIP +":"+sServidorPorta+ "/produtos/"+id);

        if (json == null || json.isEmpty()) {
            if (sServidorIP == null || sServidorIP.isEmpty()) {
                throw new IOException("IP do servidor não configurado!\nNenhum produto cadastrado no celular.");
            } else {
                throw new IOException("Produto não encontrado");
            }
        } else {
            produto = parserJSONProduto(json);

        }

        return produto;
    }

    public static  void setProduto(Produto produto) throws IOException, JSONException {
        String sServidorIP = getString(MyApp.getContext(), "cfgServidorIP");
        String sServidorPorta = getString(MyApp.getContext(), "cfgServidorPorta");

        HttpHelper helper = new HttpHelper();
        helper.setContentType("application/json");
        helper.setCharsetToEncode("UTF-8");
        JSONObject produtoJson  = new JSONObject();

        if (produto.getImagens().size() > 0) {
            List<String> imagems = new ArrayList<>();
            List<Bitmap> bitmaps = produto.getImagens();
            for (Bitmap bitmap : bitmaps) {
                imagems.add(IOUtils.encodeTobase64(bitmap));
                produtoJson.put("imagem"+produto.getImagens().size(), imagems.get(imagems.size()-1));
            }

        }



        produtoJson.put("nome", produto.getNome());
        produtoJson.put("valor", produto.getPreco());
        produtoJson.put("descricao", produto.getDescricao());
        produtoJson.put("dtEntrada", produto.getDtEntrada());
        produtoJson.put("dtSaida", produto.getDtSaida());


        if (produto.getId() == 0) {
            helper.doPost("http://" + sServidorIP + ":"+sServidorPorta+"/produtos", produtoJson, "UTF-8");
        }else {
            helper.doPut("http://" + sServidorIP + ":"+sServidorPorta+"/produtos/"+produto.getId(), produtoJson, "UTF-8");
        }


    }

    public static void deleteProduto(Long id) throws IOException {
        HttpHelper httpHelper = new HttpHelper();
        String sServidorIP = getString(MyApp.getContext(), "cfgServidorIP");
        String sServidorPorta = getString(MyApp.getContext(), "cfgServidorPorta");

        httpHelper.doDelete("http://" + sServidorIP + ":"+sServidorPorta+"/produtos/"+id);

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

    public static Bitmap parserBitmap(String params) {
        Bitmap bitmap = null;

        bitmap = BitmapFactory.decodeByteArray(params.getBytes(), 0, params.getBytes().length);
        return bitmap;
    }


    private static List<Produto> parserJSON(String json) {
        List<Produto> produtos = new ArrayList<>();

        try {
            JSONArray produtosA = new JSONArray(json);

            for (int i = 0; i < produtosA.length(); i++) {
                JSONObject jsonObj = produtosA.getJSONObject(i);
                Produto p = new Produto();
                String imagem1 = jsonObj.optString("imagem1");
                String imagem2 = jsonObj.optString("imagem2");
                String imagem3 = jsonObj.optString("imagem3");
                String imagem4 = jsonObj.optString("imagem4");
                String imagem5 = jsonObj.optString("imagem5");



                Bitmap imagemBitmap1 = IOUtils.decodeBase64(imagem1);
                Bitmap imagemBitmap2 = IOUtils.decodeBase64(imagem2);
                Bitmap imagemBitmap3 = IOUtils.decodeBase64(imagem3);
                Bitmap imagemBitmap4 = IOUtils.decodeBase64(imagem4);
                Bitmap imagemBitmap5 = IOUtils.decodeBase64(imagem5);

                p.setId(jsonObj.optLong("id"));
                p.setDescricao(jsonObj.optString("descricao"));
                p.setPreco(jsonObj.optDouble("valor"));
                p.setNome(jsonObj.optString("nome"));
                p.setDtEntrada(jsonObj.optString("dtEntrada"));
                p.setDtSaida(jsonObj.optString("dtSaida"));
                p.addImagens(imagemBitmap1);
                p.addImagens(imagemBitmap2);
                p.addImagens(imagemBitmap3);
                p.addImagens(imagemBitmap4);
                p.addImagens(imagemBitmap5);

                produtos.add(p);
            }
        } catch (JSONException e) {
            Log.i("Daniel", e.getMessage());
            produtos.clear();
        }

        return produtos;
    }

    private static Produto parserJSONProduto(String json) {


        try {
            Produto produto = new Produto();
            // Json objeto não recebe json array!

            JSONObject jsonObj = new JSONObject(json);




            String imagem1 = jsonObj.optString("imagem1");
            String imagem2 = jsonObj.optString("imagem2");
            String imagem3 = jsonObj.optString("imagem3");
            String imagem4 = jsonObj.optString("imagem4");
            String imagem5 = jsonObj.optString("imagem5");

            Bitmap imagemBitmap1 = IOUtils.decodeBase64(imagem1);
            Bitmap imagemBitmap2 = IOUtils.decodeBase64(imagem2);
            Bitmap imagemBitmap3 = IOUtils.decodeBase64(imagem3);
            Bitmap imagemBitmap4 = IOUtils.decodeBase64(imagem4);
            Bitmap imagemBitmap5 = IOUtils.decodeBase64(imagem5);

            produto.setId(jsonObj.optLong("id"));
            produto.setDescricao(jsonObj.optString("descricao"));
            produto.setPreco(jsonObj.optDouble("valor"));
            produto.setNome(jsonObj.optString("nome"));
            produto.setDtEntrada(jsonObj.optString("dtEntrada"));
            produto.setDtSaida(jsonObj.optString("dtSaida"));
            produto.addImagens(imagemBitmap1);
            produto.addImagens(imagemBitmap2);
            produto.addImagens(imagemBitmap3);
            produto.addImagens(imagemBitmap4);
            produto.addImagens(imagemBitmap5);

            return produto;

        } catch (JSONException e) {
            Log.i("Daniel", e.getMessage());

        }

        return null;
    }




}
