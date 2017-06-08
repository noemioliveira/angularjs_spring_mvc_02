var app = angular.module('loja', [ 'ngRoute','ngResource','ngAnimate']);

app.controller('filterController',function($scope){
	$scope.friends =[
	    {
	      name:"Mario",
		  lastName:"Souza",
		  age : 20
		},
		 {
			name:"Julia",
			lastName:"Eduarda",
			age :2 
		  },
		  {
			name:"Nely",
			lastName:"fernandes",
			age :64 
		   },
		   {
				name:"Luis",
				lastName:"Mehl",
				age :28 
			   }
	    ];
	});

//Criação  de  serviço
//aap.factory('UserService', function(){
//	var  users = ["Ivete","Alex","Paulo"];//poderia  vir  do db
//	return{
//		 all: function (){
//			 return users;
//		 },
//		 primeiro:  function(){
//        	return users[0]; 
//         }		 
//	};
//});
//primeiroUserController = app.controller('primeiroUserController',function($scope,UserService){
//	$scope.primeiro = UserService.primeiro();
//});

//primeiroUserController.$inject = ['$scope','UserService'];//$inject injeção de  dependencia  no  Angular

//todosUserController = app.controller('todosUserController',function($scope,UserService){
//	$scope.todos = UserService.all();
//});

//todosUserController.$inject = ['$scope','UserService'];//$inject injeção de  dependencia  no  Angular


app.controller('formCtrl', function($scope){
	$scope.master = {firstName:"Noemi", lastName:"Oliveira"};
	$scope.reset = function(){
		$scope.user = angular.copy($scope.master);
	};
	$scope.reset();
});

 app.controller('controllerClickme2', function($scope){
	 $scope.showMe = false;
	 $scope.myFunc = function(){
		 $scope.showMe = !$scope.showMe;
	 };
	});
app.controller('controllerCordinates',function($scope){
	$scope.myFunc = function(myE){
		$scope.x = myE.clientX;
		$scope.y = myE.clientY;
	};
	
});

app.controller('controllerPessoa', function($scope, $resource){
	
	//com spreing framework restful
	//pessoas = $resource("/pessoas/:codPessoa");
	
	// com servlet
	pessoas = $resource("/angularjs_spring_mvc_02/pessoas/?codPessoa=:codPessoa");
	$scope.getPorId=  function(){
		//ajax
		pessoas.get({codPessoa: $scope.codPessoa},function(data){
			 $scope.objetoPessoa = data;
		});
	};
});




app.controller('namesController', function($scope){
	$scope.names = ["Luiz","Habner","Fernando"];
});

app.controller('pegarResposta', function($scope, $http){
	$scope.pegarResposta = function(){
		$http.get("pegarResposta").then(function(response){
			document.getElementById("resposta").value = " "+ response.data;
		});
	};
});

app.controller('pegarResposta2', function($scope, $http){
	$scope.pegarResposta2 = function(){
		$http.get("pegarResposta").then(function(response){
			document.getElementById("resposta2").value = "" + response.data;
			document.getElementById("resposta3").value = "" + response.status;
			document.getElementById("resposta4").value = "" + response.statusText;
		});
	};
});

app.controller('pegarRespostaErro', function($scope,$http){
	$scope.pegarRespostaErro =  function(){
		$http.get('pegarRespostaErro').then(function(response){
			document.getElementById("respostaerro").value = "" + response.statusText;
		}, function(response){
			document.getElementById("respostaerro").value = "" + response.satusText;
		});
	};
});


app.controller('pegarRespostaJson', function($scope, $http){
	  $http.get("pegarRespostaJson").then(function(response){
		$scope.listaJson = response.data;
	});
});

app.controller('localizacao', function($scope, $location){
	$scope.myUrl =$location.absUrl();
});
app.controller('personController', function($scope){
	$scope.firstName="Noemi";
	$scope.lastName= "Oliveira";
});

app.controller('ControllerTimeOut', function($scope,$timeout){
	$scope.timermsg="oi";
	$timeout(function(){
		$scope.timermsg = "Oi depois de  3  segundos";
	},3000);
});

app.controller('controllerIntervalo', function($scope, $interval){
	$scope.intervalo =  new  Date().toLocaleTimeString();
	$interval(function(){
		$scope.intervalo = new Date().toLocaleTimeString();
	},1000);
});

app.controller('costCtrl', function($scope){
	$scope.price = 58;
});

app.controller('namesCtrl', function($scope){
	$scope.names= [
	           {name: 'Julia', country: 'Curitiba'},  
	           {name: 'Noemi', country: 'Sao Jose  dos Pinhais'}, 
	           {name: 'Nataly', country: 'Colombo'}, 
	           {name: 'Alice', country: 'Araucaria'}, 
	           {name: 'Gabriele', country: 'Pinhais'}, 
	           {name: 'Giovana', country: 'Campo Largo'}, 
	           {name: 'Arthur', country: 'Fasenda Rio Grande'}, 
	           {name: 'Samuel', country: 'Matinhos'}, 
	           {name: 'Natam', country: 'Guaratuba'}
	               ];
});

app.controller('namesCtrl2', function($scope){
	$scope.names= [
	               'Julia',
	               'Noemi',
	               'Nataly',
	               'Alice',
	               'Giovana'];
});

app.controller('namesCtrl3', function($scope){
	$scope.names= [
	               'Julia',
	               'Noemi',
	               'Nataly',
	               'Alice',
	               'Giovana'];
});

app.controller('namesCtrl4', function($scope){
	$scope.names= [
	  {name: 'Noemi', country: 'Brasil'},
	  {name: 'Nely', country: 'Paraguai'},
	  {name: 'Julia', country: 'Alaska'},
	  {name: 'Nataly', country: 'Canada'},
	  {name: 'Giovana', country: 'Havai'},
	  {name: 'Alice', country: 'Italia'},
	  {name: 'Amanda', country: 'Brasil'},
	  {name: 'Arthur', country: 'França'},
	  {name: 'Natam', country: 'Argentina'},
	  {name: 'Samuel', country: 'Mexico'}
	  ];
	$scope.orderByMe =  function(x){
		$scope.myOrderBy = x;
	};
});

//Config  -> Use  este método para registrar  o trabalho  que precisa ser  executado no  carregamento da página
//When   -> Adiciona uma nova  definição de rota ao  serviço $ route
//Otherwise -> Define  a definição de rota que  será usada na mudança de rota quando nenhuma outra rota for definida
app.config(function($routeProvider){
	$routeProvider
	.when("/",{controller:"listController",templateUrl: "list.html"})//listar
	.when("/edit/:name",{controller: "editController",templateUrl: "form.html"})
	.when("/new",{controller: "newController", templateUrl: "form.html"})
	.otherwise({redirectTo: "/"});
});

//registro de  trabalho que  deve  ser  realizado quando o injetor  é  feito carregando  todos os  modulos
app.run(function($rootScope){
	$rootScope.frutas = ['Banana', 'Abacaxi', 'Maça','Laranja'];
});

app.controller('listController',['$scope','$routeParams','$route','$location', 
                                 function($scope,$routeParams,$route,$location){	
}]);
	
//$scope => é o escopo da  aplicação HTML
//$location => é redirecionamento entre rotas
//$routeParamss => São os parametros passados pela URL
app.controller('editController', ['$scope','$routeParams','$rootScope','$route','$location',
           function editController($scope, $routeParams, $rootScope, $route,$location){
	$scope.title = 'Editar Frutas';
	$scope.fruta =$routeParams.name ;
	$scope.frutaIndex = $scope.frutas.indexOf($scope.fruta);
	
	$scope.salvar = function(){
		$scope.frutas[$scope.frutaIndex] = $scope.fruta;
		$location.path('/');
	};
}]);

app.controller('newController',['$scope','$routeParams','$rootScope','$route','$location',
          function newController($scope, $routeParams,$rootScope,$route,$location){
	$scope.title = 'Nova Fruta';
	$scope.fruta = '';
	
	$scope.salvar = function(){
		$scope.frutas.push($scope.fruta);//add nova fruta
		$location.path('/');
	};
}]);

//function listController($scope){
//	
//}
//app.controller('primeiroController',['$scope', function($scope){
//	$scope.user = {meuNome :'Noemi'};
//	
//	$scope.contador = 0;
//	
//	$scope.addContador = function(){
//		$scope.contador ++;
//	};
//	
//	
//	$scope.pessoas = ['Júlia','Noemi','Nely'];
//}]);
	
