#ifndef COMPARATORS_H
#define COMPARATORS_H

#include "Tranzactie.h"

inline bool cmp_dupa_data(const Tranzactie& t1, const Tranzactie& t2) {
    return t1.getData() < t2.getData();
}

inline bool cmp_dupa_suma(const Tranzactie& t1, const Tranzactie& t2) {
    return t1.getSuma() < t2.getSuma();
}

inline bool cmp_dupa_bancnote(const Tranzactie& t1, const Tranzactie& t2) {
    return t1.getNumarBancnote() < t2.getNumarBancnote();
}

#endif // COMPARATORS_H
