#include "repo.h"
#include"exceptii.h"

Repo::Repo(){};

Repo::~Repo(){};

void Repo::addItem(const Produs& p) {
    for (const auto&produs:produse) {
        if (produs.get_cod() == p.get_cod()) {
            throw ExceptieRepo("Exista deja un produs  cu codul" + to_string(p.get_cod()));

        }
    }
    produse.push_back(p);
}

vector<Produs> Repo::getAll() const {
    return produse;
}
 int Repo::size()const {
    return produse.size();
}

void Repo::deleteItem(int cod) {
    for (auto it=produse.begin();it!=produse.end();it++) {
        if (it->get_cod()==cod) {
            produse.erase(it);
            return;
        }
    }
    throw ExceptieRepo("Produsul cu codul"+ to_string(cod)+"nu exista si nu poate fi sters!");
}

void Repo::updateItem(int cod,const Produs& nou) {
    for (auto&p:produse) {
        if (p.get_cod()==cod) {
            p.set_nume(nou.get_nume());
            p.set_pret(nou.get_pret());
            return;
        }
    }
    throw ExceptieRepo("Produsul cu codul"+to_string(cod)+"nu exista si nu poate fi actualizat!");
}