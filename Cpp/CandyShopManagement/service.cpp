#include "service.h"
#include<cstring>
#include"repo.h"
#include<string>
#include<vector>
#include<stdexcept>
#include<sstream>
#include<iomanip>
#include"exceptii.h"
#include"validator.h"

Service::Service(RepoFile&r):repo(r){};

void Service::adauga_produs(int c,const string&n,double p) {
    Validator::valideazaToate(c,n,p);
    for (const auto& produs : repo.getAll()) {
            if (produs.get_cod() == c) {
                throw ExceptieRepo ("Produs cu acest cod exista deja!");
            }
        }
        Produs produs(c, n, p);
        repo.addItem(produs);
    }


vector<Produs>Service::get_all()const {
    return repo.getAll();
}

void  Service::deleteItem(int cod) {
     repo.deleteItem(cod);
}

void Service::updateItem(int cod,string n,double p) {
    Validator::valideazaToate(cod,n,p);
    Produs produs(cod,n,p);
    repo.updateItem(cod,produs);
}

string Service::achizitieProdus(int cod, double sumaClient) {
    Validator::valideazaCod(cod);
    for (const auto& p : repo.getAll()) {
        if (p.get_cod() == cod) {
            return tonomatBani.achizitie(p.get_pret(),sumaClient);
        }
    }
    throw ExceptieRepo("Produsul nu exista!");
}

std::string Service::getMonedeDisponibile() const {
    return tonomatBani.afiseazaMonedeDisponibile();
}

void Service::adaugaMonedeTonomat(const std::vector<double>&monede) {
    tonomatBani.adaugaMonede(monede);
}
double Service::getSumaTotalaInTonomat() const {
    return tonomatBani.getSumaTotala();
}