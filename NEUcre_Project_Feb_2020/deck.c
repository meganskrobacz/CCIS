// Author: Megan Skrobacz
// Class: CS5006, Evening Section
// Due: February 14, 2020 by midnight (milestone 2)


#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "a4.h"

#define PRINT_DEBUG 1


// Creates the deck, initializing any fields necessary.
// Returns a pointer to the deck, which has been allocated to the heap.
Deck* CreateDeck() {
  Deck* deck = (Deck*)malloc(sizeof(Deck));
  if (deck == NULL) {
    printf("Malloc failed for creating deck.");
    return NULL;
  }
  deck->top_card = -1;
  return deck;
}

// Destroys an individual card, thus preventing a
// memory leak.
void DestroyCard(Card* card_ptr) {
  free(card_ptr);
}

// Removes all the cards from the deck and frees() them,
// thus preventing a memory leak.
void DestroyDeck(Deck* deck) {
  while (IsDeckEmpty(deck) == 0) {
    Card* to_free = PopCardFromDeck(deck);
    DestroyCard(to_free);
    free(deck->cards[deck->top_card]);
    deck->top_card--;
  }
  free(deck);
}

// Adds a card to the top of the deck.
// Returns a pointer to the deck.
Deck* PushCardToDeck(Card* card_ptr, Deck* deck_ptr) {
  if (deck_ptr->top_card < kNumCardsInDeck - 1) {
    deck_ptr->top_card++;
    deck_ptr->cards[deck_ptr->top_card] = card_ptr;
  }
  return deck_ptr;
}

// Shows the top card, but does not remove it from the stack.
// Returns a pointer with the top card.
Card* PeekAtTopCard(Deck* deck_ptr) {
  if (deck_ptr->top_card == -1) {
    printf("No cards in the deck");
    return NULL;
  } else {
    return deck_ptr->cards[deck_ptr->top_card];
  }
}

// Removes the top card from the deck and returns it.
// Returns a pointer to the top card in the deck.
Card* PopCardFromDeck(Deck* deck_ptr) {
  if (deck_ptr->top_card == -1) {
    printf("No cards left in the deck");
    return NULL;
  } else {
    Card* temp_card = deck_ptr->cards[deck_ptr->top_card];
    deck_ptr->cards[deck_ptr->top_card] = NULL;
    deck_ptr->top_card--;
    return temp_card;
  }
}

// Determines if the deck is empty.
// Returns 0 if the Deck has any cards; 1 otherwise.
int IsDeckEmpty(Deck* deck_ptr) {
  if (deck_ptr->top_card == -1) {
    return 1;
  }
  return 0;
}

// Helper function which creates a Card object.
Card* CreateCard(Suit suit, Name name) {
  Card* new_card = (Card*)malloc(sizeof(Card));
  new_card->name = name;
  new_card->suit = suit;
  new_card->value = -1;
  return new_card;
}


// Creates all the cards and pushes them into the Deck.
Deck* PopulateDeck() {
  Deck *deck_ptr = CreateDeck();
  for (int i = NINE; i <= ACE; i++) {
    for (int j = HEARTS; j <= DIAMONDS; j++) {
      PushCardToDeck(CreateCard(j, i), deck_ptr);
    }
  } return deck_ptr;
}

// Takes all the cards in the deck, rearranges the order,
// and pushes the cards back into the Deck.
void Shuffle(Deck* deck_ptr) {
  for (int i = 0; i < (kNumCardsInHand * kNumCardsInDeck); i++) {
    int random_index1 = rand() % ((deck_ptr->top_card) - 1);
    int random_index2 = rand() & ((deck_ptr->top_card) - 1);
    Card* temp_card1 = deck_ptr->cards[random_index1];
    deck_ptr->cards[random_index1] = deck_ptr->cards[random_index2];
    deck_ptr->cards[random_index2] = temp_card1;
  }
}

// Given a Deck (assume it is already shuffled), take
// the top card from the deck and alternatively give
// it to player 1 and player 2, until they both have
// kNumCardsInHand.
void Deal(Deck* deck_ptr, Hand* p1_hand, Hand* p2_hand) {
  for (int i = 0; i < kNumCardsInHand; i++) {
    AddCardToHand(deck_ptr->cards[deck_ptr->top_card], p1_hand);
    deck_ptr->top_card--;
    AddCardToHand(deck_ptr->cards[deck_ptr->top_card], p2_hand);
    deck_ptr->top_card--;
  }
}

// Take all the cards out of a given hand, and put
// them back into the deck.
void ReturnHandToDeck(Hand* hand_ptr, Deck* deck_ptr) {
  Hand* temp = hand_ptr;
  while (temp->num_cards_in_hand > 0) {
    Card* remove = RemoveCardFromHand(hand_ptr->first_card->this_card,
                                      temp);
    PushCardToDeck(remove, deck_ptr);
    temp->first_card = temp->first_card->next_card;
    temp->num_cards_in_hand--;
  }
}
