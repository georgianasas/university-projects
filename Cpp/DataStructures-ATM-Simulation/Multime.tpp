#include <iostream>
#include "Multime.h"

template<typename T>
Multime<T>::Multime(Comparator<T> cmpFunc) {
    head = nullptr;
    comp = cmpFunc;
}

template<typename T>
Multime<T>::~Multime() {
    clear();
}

template<typename T>
void Multime<T>::clear() {
    while (head) {
        NodM<T>* temp = head;
        head = head->next;
        delete temp;
    }
}


template<typename T>
bool Multime<T>::ifExist(const T& e) const {
    NodM<T>* curr = head;
    while (curr) {
        if (!comp(curr->elem, e) && !comp(e, curr->elem)) {
            return true;
        }
        curr = curr->next;
    }
    return false;
}

template<typename T>
void Multime<T>::addtr(const T& e) {
    if (ifExist(e)) return;  // Nu adăugăm dacă există deja

    NodM<T>* nou = new NodM<T>{e, nullptr};

    if (!head || comp(e, head->elem)) {
        // Inserăm la început dacă lista e goală sau e mai mic decât capul listei
        nou->next = head;
        head = nou;
        return;
    }

    NodM<T>* curr = head;
    while (curr->next && comp(curr->next->elem, e)) {
        curr = curr->next;
    }

    nou->next = curr->next;
    curr->next = nou;
}

template<typename T>
int Multime<T>::remove(const T& e) {
    NodM<T>* curr = head;
    NodM<T>* prev = nullptr;

    while (curr) {
        if (!comp(curr->elem, e) && !comp(e, curr->elem)) {
            if (prev) {
                prev->next = curr->next;
            } else {
                head = curr->next;
            }
            delete curr;
            return 1;
        }
        prev = curr;
        curr = curr->next;
    }
    return 0;
}


template<typename T>
int Multime<T>::size() const {
    int count = 0;
    NodM<T>* curr = head;
    while (curr) {
        count++;
        curr = curr->next;
    }
    return count;
}


template<typename T>
T Multime<T>::getElem(int poz) const {
    int idx = 0;
    NodM<T>* curr = head;
    while (curr) {
        if (idx == poz) return curr->elem;
        idx++;
        curr = curr->next;
    }
    return T(); // Dacă poziția nu există, returnăm un element gol
}