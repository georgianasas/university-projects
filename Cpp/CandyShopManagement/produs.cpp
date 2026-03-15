#include<string>
#include"exceptii.h"
#include "validator.h"
#include"produs.h"

Produs::Produs() {
    this->cod=0;
    this->nume="";
    this->pret=0.0;

}

Produs::Produs(int c,string n,double p) {
    Validator::valideazaToate(c,n,p);
    cod=c;
    nume=n;
    pret=p;
}

int Produs::get_cod()const {
    return this->cod;

}

string Produs::get_nume()const {
    return this->nume;
}

double Produs::get_pret()const {
    return this->pret;
}

void Produs::set_cod(int c) {
    this->cod=c;
}

void Produs::set_nume(const string &n) {
    this->nume=n;
}

void Produs::set_pret(double p) {
    this->pret=p;
}

Produs& Produs::operator=(const Produs& other) {
    if(this!=&other) {
        this->cod=other.cod;
        this->nume=other.nume;
        this->pret=other.pret;

    }
    return *this;
}