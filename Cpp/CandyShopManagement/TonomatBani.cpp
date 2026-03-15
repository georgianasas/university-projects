#include "TonomatBani.h"
#include "exceptii.h"
#include <sstream>
#include <iomanip>
#include <cmath>
#include<algorithm>

TonomatBani::TonomatBani() {
    initMonede();
}

void TonomatBani::initMonede() {
    monedeDisponibile = {10,10, 10,10,5, 5,5,5,5, 1, 1, 1,1,1,1, 0.5, 0.5,0.5,0.5,0.5};
}

std::string TonomatBani::achizitie(double pret, double sumaClient) {
    if (sumaClient < pret)
        throw ExceptieTonomat("Fonduri insuficiente!");

    double rest = sumaClient - pret;
    rest = std::round(rest * 100) / 100;

    std::vector<double> restReturnat;
    std::sort(monedeDisponibile.begin(), monedeDisponibile.end(), std::greater<>());
    for (size_t i = 0; i < monedeDisponibile.size(); ++i) {
        if (rest >= monedeDisponibile[i]) {
            restReturnat.push_back(monedeDisponibile[i]);
            rest -= monedeDisponibile[i];
            rest = std::round(rest * 100) / 100;
            monedeDisponibile.erase(monedeDisponibile.begin() + i);
            i = -1;
        }
        if (rest == 0.0)
            break;
    }

    if (rest > 0.001)
        throw ExceptieTonomat("Tonomatul nu poate da rest exact!");

    std::ostringstream mesaj;
    mesaj << std::fixed << std::setprecision(2);
    mesaj << "Achizitie reusita. Rest: " << (sumaClient - pret) << " lei (monede: ";
    for (double m : restReturnat)
        mesaj << m << " ";
    mesaj << ")";

    return mesaj.str();
}

std::string TonomatBani::afiseazaMonedeDisponibile() const {
    std::ostringstream out;
    out << "Monede disponibile in tonomat: ";
    for (double m : monedeDisponibile)
        out << std::fixed << std::setprecision(2) << m << " ";
    return out.str();
}
void TonomatBani::adaugaMonede(const std::vector<double>&monede) {
    for (double m:monede) {
        if (m!=0.5&&m!=1&&m!=5&&m!=10) {
            throw ExceptieTonomat("Moneda invalida: "+ std::to_string(m));
        }
        monedeDisponibile.push_back(m);
    }
}

double TonomatBani::getSumaTotala()const {
    double total=0;
    for (double m:monedeDisponibile)
        total+=m;
    return total;

}
