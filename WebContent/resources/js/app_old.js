var app = angular.module('loja', [ 'ngRoute','ngResource','ngAnimate']);

app.config(function($routeProvider,$provide, $httpProvider,$locationProvider){
	
	$routeProvider.when("/clienteList",{
		controller : "clienteController",
		templateUrl: "cliente/list.html"
	})// listar
	.when("/clienteedit/:id", {
		controller: "clienteController",
		templateUrl: "cliente/cadastro.html"
	})// editar
	.when("/cliente/cadastro",{
		controller: "clienteController",
		templateUrl: "cliente/cadastro.html",
	})// novo
	.otherwise({
		redirectTo: "/",
	});
});

app.controller('clienteController', function($scope,$http,$location,$routeParams) {
	
	
  if($routeParams.id != null && $routeParams.id != undefined 
			  &&  $routeParams.id  != ''){
		  //entra  p/ editar		  
         $http.get("cliente/buscarcliente/" + $routeParams.id).success(function(response){
			$scope.cliente = response;
			sucesso("Editado com sucesso!");
		}).error(function(data,status, headers, config){
				erro("Erro ao  buscar cliente",status);
			});  
		  
	  }else{
		  $scope.cliente = {};
	  }	  
	   $scope.editarCliente = function(id){
		   $location.path('clienteedit/' + id);
		
	   };
		
	
	   $scope.salvarCliente = function() {
//			$scope.cliente.foto = document.getElementById("imagemCliente").getAttribute("src");
			
			$http.post("cliente/salvar", $scope.cliente).success(function(response) {
				$scope.cliente = {};
//				document.getElementById("imagemCliente").src = '';
				sucesso("Gravado com sucesso!");
			}).error(function(response) {
				erro("Error: " + response);
			});

 };	
				
	// editar
//	$scope.editandoCliente =  function name(){
//       
//	};		
//	
	
	// listar Todos
	$scope.listarClientes =  function(){
		$http.get("cliente/listar").success(function(response){
			$scope.data = response;
		}).error(function(response){
			erro("Erro ao  remover cliente",status);
		});
		
	};
	// apagar
	$scope.removerCliente = function(codCliente){
		$http.delete("cliente/deletar/" + codCliente).success(function(response){
			$scope.listarClientes();
			sucesso("Removido com sucesso!");
		}).error(function(data,status, headers, config){
//			alert("Error:" + status);
			erro("Erro ao  remover cliente",status);
		});
	};

	// carrega os estados ao iniciar a tela de cadastro 
	$scope.carregarEstados = function() {
		$scope.dataEstados = [{}];
		$http.get("estados/listar").success(function(response) {
			$scope.dataEstados = response;
		}).error(function(response) {
			erro("Error ao carregar Estados: " + response);
		});
	};
	
	// carrega as cidades de acordo com o estado passado por parametro
	$scope.carregarCidades = function(estado) {
		if (identific_nav() != 'chrome') {// executa se for diferente do chrome
			$http.get("cidades/listar/" + estado.id).success(function(response) {
				$scope.cidades = response;
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
	  }
	};
	
	function carregarCidadesChrome(estado) {
		if (identific_nav() === 'chrome') {// executa se for chrome
			$.get("cidades/listarchrome", { idEstado : estado.value}, function(data) {
				 var json = JSON.parse(data);
				 html = '<option value="">--Selecione--</option>';
				 for (var i = 0; i < json.length; i++) {
					  html += '<option value='+json[i].id+'>'+json[i].nome+'</option>';
				 }
				 $('#selectCidades').html(html);
			});
	  }
	}



});

function sucesso(msg){
	 $.notify({
		 message: msg
	 },{
		 type: 'success',
		 timer: 1000
	 });
};

function erro(msg){
	 $.notify({
		 message: msg
	 },{
		 type: 'danger',
		 timer: 1000
	 });
};



