// create the module and name it scotchApp
var scotchApp = angular.module('scotchApp', ['ngRoute'])
     .value('urlBase', 'http://localhost:8081/avaliacao/rest/');

// configure our routes
scotchApp.config(function($routeProvider) {
    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : 'pages/cliente.html',
            controller  : 'clienteController'
        })

        // route for the about page
        .when('/conta', {
            templateUrl : 'pages/conta.html',
            controller  : 'contaController'
        })

        // route for the contact page
        .when('/movimentacao', {
            templateUrl : 'pages/movimentacao.html',
            controller  : 'movimentacaoController'
        });
});

// create the controller and inject Angular's $scope

scotchApp.controller("clienteController", function ($scope, $http, urlBase) {
    // create a message to display in our view
    var self = this;
    self.usuario = 'Silas Cerqueira';

    self.clientes = [];
    self.cliente = undefined;

    self.novo = function () {
        self.cliente = {};
    };

    self.validar = function(cliente){
        if(cliente.nome == undefined){
            self.ocorreuErro("Por favor preencha o nome!")
            return false;
        }else if(cliente.email == undefined){
            self.ocorreuErro("Por favor preencha com um email valido!")
            return false;
        }else if(cliente.cpf == undefined){
            self.ocorreuErro("Por favor preencha com um cpf valido!")
            return false;
        }else if(cliente.dataDeNascimento == undefined){
            self.ocorreuErro("Por favor preencha o data de nascimento!")
            return false;
        }else if(cliente.endereco == undefined){
            self.ocorreuErro("Por favor preencha o endereco!")
            return false;
        }
        return true;
    }

    self.salvar = function () {
        if(self.validar(self.cliente)) {
            var metodo = 'POST';
            if (self.cliente.id) {
                metodo = 'PUT';
            }

            $http({
                method: metodo,
                url: urlBase + 'clientes/',
                data: self.cliente
            }).then(function successCallback(response) {
                self.atualizarTabela();
            }, function errorCallback(response) {
                self.ocorreuErro(response);
            });
        }
    };

    self.alterar = function (cliente) {
        self.cliente = cliente;
    };

    self.deletar = function (cliente) {
        self.cliente = cliente;

        $http({
            method: 'DELETE',
            url: urlBase + 'clientes/' + self.cliente.id + '/'
        }).then(function successCallback(response) {
            self.atualizarTabela();
        }, function errorCallback(response) {
            self.ocorreuErro(response);
        });
    };

    self.ocorreuErro = function (mensagem) {
        alert(mensagem);
    };

    self.atualizarTabela = function () {
        $http({
            method: 'GET',
            url: urlBase + 'clientes/'
        }).then(function successCallback(response) {
            self.clientes = response.data;
            self.cliente = undefined;
        }, function errorCallback(response) {
            self.ocorreuErro(response);
        });
    };

    self.activate = function () {
        self.atualizarTabela();
    };
    self.activate();
});

scotchApp.controller('contaController', function($scope, $http, urlBase) {
        // create a message to display in our view
        var self = this;
        self.usuario = 'Silas Cerqueira';

        self.contas = [];
        self.conta = undefined;
        self.cars = [];

        self.novo = function () {
            self.conta = {};
            $http({
                method: 'GET',
                url: urlBase + 'clientes/'
            }).then(function successCallback(response) {
                self.cars = response.data;
            }, function errorCallback(response) {
                self.ocorreuErro(response);
            });
        };

        self.validar = function(conta){
            if(conta.cliente == undefined){
                self.ocorreuErro("Por favor preencha o cliente!")
                return false;
            }else if(conta.saldo == undefined){
                self.ocorreuErro("Por favor preencha o saldo!")
                return false;
            }
            return true;
        }

        self.salvar = function () {
            if(self.validar(self.conta)) {
                var metodo = 'POST';
                if (self.conta.id) {
                    metodo = 'PUT';
                }
                $http({
                    method: metodo,
                    url: urlBase + 'contas/',
                    data: self.conta
                }).then(function successCallback(response) {
                    self.atualizarTabela();
                }, function errorCallback(response) {
                    self.ocorreuErro(response);
                });
            }
        };

        self.alterar = function (conta) {
            self.conta = conta;
            $http({
                method: 'GET',
                url: urlBase + 'clientes/'
            }).then(function successCallback(response) {
                self.cars = response.data;
            }, function errorCallback(response) {
                self.ocorreuErro(response);
            });
            for (i = 0; i < self.cars.length; i++) {
                if(self.cars[i].id == conta.cliente.id)
                    self.conta.cliente.id = self.cars[i].id;
            }
        };

        self.deletar = function (conta) {
            self.conta = conta;

            $http({
                method: 'DELETE',
                url: urlBase + 'contas/' + self.conta.id + '/'
            }).then(function successCallback(response) {
                self.atualizarTabela();
            }, function errorCallback(response) {
                self.ocorreuErro(response);
            });
        };

        self.ocorreuErro = function (mensagem) {
            alert(mensagem);
        };

        self.atualizarTabela = function () {
            $http({
                method: 'GET',
                url: urlBase + 'contas/'
            }).then(function successCallback(response) {
                self.contas = response.data;
                self.conta = undefined;
            }, function errorCallback(response) {
                self.ocorreuErro(response);
            });
        };

        self.activate = function () {
            self.atualizarTabela();
        };
        self.activate();


        $scope.message = 'Look! I am an about page.';
});

scotchApp.controller('movimentacaoController', function($scope, $http, urlBase) {

    var self = this;
    self.usuario = 'Silas Cerqueira';

    self.movimentacoes = [];
    self.movimentacao = undefined;
    self.contas = [];
    self.tipoMovimentacoes = [
        {descricao : "Transfêrencia", value : 0},
        {descricao : "Saque", value : 1},
        {descricao : "Deposito", value : 2}
    ];

    self.novo = function () {
        self.movimentacao = {};
        $http({
            method: 'GET',
            url: urlBase + 'contas/'
        }).then(function successCallback(response) {
            self.contas = response.data;
        }, function errorCallback(response) {
            self.ocorreuErro(response);
        });
    };

    self.validar = function(movimentacao){
        if(movimentacao.contaCorrente == undefined){
            self.ocorreuErro("Por favor preencha a conta!");
            return false;
        }else if(movimentacao.valor == undefined){
            self.ocorreuErro("Por favor preencha o valor!");
            return false;
        }else if(movimentacao.tipoMovimentacao == undefined){
            self.ocorreuErro("Por favor preencha o Tipo Movimentação!");
            return false;
        }else if(movimentacao.tipoMovimentacao == 0){
            if(movimentacao.contaCorrenteFavorecido == undefined){
                self.ocorreuErro("Por favor preencha a Conta Favorecida!");
                return false;
            }
        }
        return true;
    }

    self.salvar = function () {
        if(self.validar(self.movimentacao)) {
            var metodo = 'POST';
            if (self.movimentacao.id) {
                metodo = 'PUT';
            }
            $http({
                method: metodo,
                url: urlBase + 'movimentacoes/',
                data: self.movimentacao
            }).then(function successCallback(response) {
                self.atualizarTabela();
            }, function errorCallback(response) {
                self.ocorreuErro(response);
            });
        }
    };

    self.ocorreuErro = function (mensagem) {
        alert(mensagem);
    };

    self.atualizarTabela = function () {
        $http({
            method: 'GET',
            url: urlBase + 'movimentacoes/'
        }).then(function successCallback(response) {
            self.movimentacoes = response.data;
            self.movimentacao = undefined;
            for (i = 0; i < self.movimentacoes.length; i++) {
                self.movimentacoes[i].data =  self.formateDate(self.movimentacoes[i].data);
                self.movimentacoes[i].tipoMovimentacao = self.dmTipoMoviemntacao(self.movimentacoes[i].tipoMovimentacao);
            }
        }, function errorCallback(response) {
            self.ocorreuErro(response);
        });
    };

    self.dmTipoMoviemntacao = function(tipoMovimentacao){
        if(tipoMovimentacao == 0){
            return "Transfêrencia";
        }
        else if(tipoMovimentacao == 1){
            return "Saque";
        }
        else if(tipoMovimentacao == 2){
            return "Deposito";
        }
    }
    self.formateDate = function(data){
        if(data != undefined){
            var timestamp = data,
                date = new Date(timestamp),
                datevalues = [
                    date.getDate(),
                    date.getMonth()+1,
                    date.getFullYear()
                ];
            return datevalues[0] +"/" + datevalues[1] + "/" + datevalues[2];
        }
    }
    self.activate = function () {
        self.atualizarTabela();
    };
    self.activate();
});