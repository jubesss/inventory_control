package br.univates.projeto_produtos.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Gerencia todas as operações de negócio relacionadas aos produtos.
 * Classe de Controle (Controller).
 *
 * @author juliano.hammes1
 */
public class ProdManager {

    private List<Produto> produtos;
    private final ObjectMapper objectMapper;
    private static final String DATA_FILE = "estoque_data.json";

    public ProdManager() {
        this.produtos = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadData();
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists() && file.length() > 0) {
            try {
                Produto[] produtosArray = objectMapper.readValue(file, Produto[].class);
                this.produtos = new ArrayList<>(Arrays.asList(produtosArray));
            } catch (IOException e) {
                System.err.println("Erro ao carregar dados do estoque: " + e.getMessage());
            }
        }
    }

    public void saveData() {
        try {
            objectMapper.writeValue(new File(DATA_FILE), produtos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do estoque: " + e.getMessage());
        }
    }

    public boolean adicionarProduto(Produto produto) {
        if (getProdutoPorCodigo(produto.getCodigo()).isPresent()) {
            return false;
        }
        this.produtos.add(produto);
        saveData();
        return true;
    }

    public boolean atualizarProduto(String codigo, Produto novoProduto) {
        Optional<Produto> produtoExistenteOpt = getProdutoPorCodigo(codigo);
        if (produtoExistenteOpt.isPresent()) {
            Produto produtoExistente = produtoExistenteOpt.get();
            produtoExistente.setNome(novoProduto.getNome());
            produtoExistente.setDescricao(novoProduto.getDescricao());
            produtoExistente.setPreco_compra(novoProduto.getPreco_compra());
            produtoExistente.setPreco_venda(novoProduto.getPreco_venda());
            produtoExistente.setQuantidade(novoProduto.getQuantidade());
            produtoExistente.setQuant_min(novoProduto.getQuant_min());
            saveData();
            return true;
        }
        return false;
    }

    public boolean removerProduto(String codigo) {
        boolean removido = this.produtos.removeIf(p -> p.getCodigo().equals(codigo));
        if (removido) {
            saveData();
        }
        return removido;
    }

    public Optional<Produto> getProdutoPorCodigo(String codigo) {
        return this.produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst();
    }
    
    

    public List<Produto> getTodosProdutos() {
        return new ArrayList<>(this.produtos);
    }

    public boolean entradaEstoque(String codigoProduto, int quantidade) {
        if (quantidade <= 0) return false;
        Optional<Produto> produtoOpt = getProdutoPorCodigo(codigoProduto);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setQuantidade(produto.getQuantidade() + quantidade);
            saveData();
            return true;
        }
        return false;
    }

    public boolean saidaEstoque(String codigoProduto, int quantidade) {
        if (quantidade <= 0) return false;
        Optional<Produto> produtoOpt = getProdutoPorCodigo(codigoProduto);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            if (produto.getQuantidade() >= quantidade) {
                produto.setQuantidade(produto.getQuantidade() - quantidade);
                saveData();
                return true;
            }
        }
        return false;
    }
    
    public boolean temEstoqueSuficiente(String codigoProduto, int quantidade) {
        return getProdutoPorCodigo(codigoProduto)
                .map(p -> p.getQuantidade() >= quantidade)
                .orElse(false);
    }

    public List<Produto> getProdutosEmAlerta() {
        List<Produto> alertas = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getQuantidade() <= produto.getQuant_min()) {
                alertas.add(produto);
            }
        }
        return alertas;
    }
}