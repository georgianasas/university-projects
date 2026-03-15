#ifndef MULTIMEORDONATA_MULTIME_H
#define MULTIMEORDONATA_MULTIME_H

template<typename T>
struct NodM {
    T elem;
    NodM* next;
};

template<typename T>
using Comparator = bool (*)(const T&, const T&);  // pointer la functie

template<typename T>
class Multime {
private:
    NodM<T>* head;
    Comparator<T> comp;
public:
    Multime(Comparator<T> cmpFunc);
    ~Multime();

    void addtr(const T& e);
    int remove(const T& e);
    bool ifExist(const T& e) const;
    int size() const;
    T getElem(int poz) const;

private:
    void clear();
};

#include "Multime.tpp"

#endif // MULTIMEORDONATA_MULTIME_H
