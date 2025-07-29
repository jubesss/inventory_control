package br.univates.projeto_produtos.negocio;

/**
 * Representa um Produto no sistema.
 * Classe de Modelo (Model).
 *
 * @author juliano.hammes1
 */
public class Produto {

    private String codigo;
    private String nome;
    private String descricao;
    private double preco_venda;
    private double preco_compra;
    private int quantidade;
    private int quant_min;

    // Construtor padrão para a desserialização do JSON (Jackson precisa dele)
    public Produto() {
    }

    public Produto(String codigo, String nome, String descricao, double preco_compra,
                   double preco_venda, int quantidade, int quant_min) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco_compra = preco_compra;
        this.preco_venda = preco_venda;
        this.quantidade = quantidade;
        this.quant_min = quant_min;
    }

    // --- Getters e Setters ---
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public double getPreco_compra() {
        return preco_compra;
    }

    public void setPreco_compra(double preco_compra) {
        this.preco_compra = preco_compra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuant_min() {
        return quant_min;
    }

    public void setQuant_min(int quant_min) {
        this.quant_min = quant_min;
    }

    @Override
    public String toString() {
        return "Produto:\n" +
                "Código - '" + codigo + '\'' +
                ", \nNome - '" + nome + '\'' +
                ", \nDescrição - '" + descricao + '\'' +
                ", \npreço de venda - '" + preco_venda + '\'' +
                ", \npreço de compra - '" + preco_compra + '\'' +
                ", \nQuantidade - '" + quantidade + '\'' +
                ", \nQuantidade mínima - '" + quant_min + '\'' +
                "\n___________________________" +

                "\n";
    }
    
    public String toStringAlerta() {
        return "Produto:\n" +
                "Código - '" + codigo + '\'' +
                ", \nNome - '" + nome + '\'' +
                ", \nQuantidade - '" + quantidade + '\'' +
                "\n___________________________" +
                "\n";
    }
}