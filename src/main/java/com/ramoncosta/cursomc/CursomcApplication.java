package com.ramoncosta.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ramoncosta.cursomc.domain.Categoria;
import com.ramoncosta.cursomc.domain.Cidade;
import com.ramoncosta.cursomc.domain.Cliente;
import com.ramoncosta.cursomc.domain.Endereco;
import com.ramoncosta.cursomc.domain.Estado;
import com.ramoncosta.cursomc.domain.ItemPedido;
import com.ramoncosta.cursomc.domain.Pagamento;
import com.ramoncosta.cursomc.domain.PagamentoComBoleto;
import com.ramoncosta.cursomc.domain.PagamentoComCartao;
import com.ramoncosta.cursomc.domain.Pedido;
import com.ramoncosta.cursomc.domain.Produto;
import com.ramoncosta.cursomc.domain.enums.EstadoPagamento;
import com.ramoncosta.cursomc.domain.enums.TipoCliente;
import com.ramoncosta.cursomc.repository.CategoriaRepository;
import com.ramoncosta.cursomc.repository.CidadeRepository;
import com.ramoncosta.cursomc.repository.ClienteRepository;
import com.ramoncosta.cursomc.repository.EnderecoRepository;
import com.ramoncosta.cursomc.repository.EstadoRepository;
import com.ramoncosta.cursomc.repository.ItemPedidoRepository;
import com.ramoncosta.cursomc.repository.PagamentoRepository;
import com.ramoncosta.cursomc.repository.PedidoRepository;
import com.ramoncosta.cursomc.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria(null, "Informatica");
		Categoria categoria2 = new Categoria(null, "Escritorio");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 1000.00);
		Produto produto3 = new Produto(null, "Mouse", 100.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2 ));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1,categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

		//########################################################################################
		
		Estado estado1 = new Estado(null, "Amazonas");
		Estado estado2 = new Estado(null, "São Paulo");
		Estado estado3 = new Estado(null, "Minas Gerais");		
		
		Cidade cidade1 = new Cidade(null, "Manaus", estado1);
		Cidade cidade2 = new Cidade(null, "Campinas", estado2);
		Cidade cidade3 = new Cidade(null, "Poços de Calda", estado3);
		Cidade cidade4 = new Cidade(null, "São Paulo", estado2);		
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade4));
		estado3.getCidades().addAll(Arrays.asList(cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2, estado3));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4));
		
		//########################################################################################
		
		Cliente cliente1 = new Cliente(null, "Ramon Costa", "ramon@gmail.com", "00000000000", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("92 995284817", "92 35816878")) ;
		Endereco endereco1 = new Endereco(null, "Rua São Miguel", "04", "Casa com Muro Alto", "Col. Santo Antonio", "1234", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Rua ABC", "07", "Casa com Muro Alto", "Cidade Nova", "43121", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		//########################################################################################
		
		SimpleDateFormat simpleDateFortmat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, simpleDateFortmat.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, simpleDateFortmat.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, simpleDateFortmat.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		//########################################################################################
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		produto1.getItens().addAll(Arrays.asList(itemPedido1));
		produto2.getItens().addAll(Arrays.asList(itemPedido3));
		produto3.getItens().addAll(Arrays.asList(itemPedido2));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1,itemPedido2, itemPedido3 ));
	}
}
