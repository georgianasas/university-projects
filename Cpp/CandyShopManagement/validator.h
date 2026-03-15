#ifndef VALIDATOR_H
#define VALIDATOR_H

#include <string>
#include "exceptii.h"

class Validator {
public:
  static void valideazaCod(int cod) {
    if (cod <= 0) {
      throw ExceptieProdus("Codul trebuie sa fie pozitiv!");
    }
  }

  static void valideazaNume(const std::string& nume) {
    if (nume.empty()) {
      throw ExceptieProdus("Produsul trebuie sa aiba un nume!");
    }
  }

  static void valideazaPret(double pret) {
    if (pret <= 0) {
      throw ExceptieProdus("Pretul trebuie sa fie pozitiv!");
    }
  }

  static void valideazaToate(int cod, const std::string& nume, double pret) {
    std::string erori;
    if (cod <= 0) erori += "Cod invalid!\n";
    if (nume.empty()) erori += "Nume invalid!\n";
    if (pret <= 0.0) erori += "Pret invalid!\n";

    if (!erori.empty()) {
      throw ExceptieProdus(erori);
    }
  }
};

#endif // VALIDATOR_H
