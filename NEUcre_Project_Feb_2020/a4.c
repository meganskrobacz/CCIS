// Author: Megan Skrobacz
// Class: CS5006, Evening Section
// Due: February 14, 2020 (milestone 2)


#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#include "a4.h"

#define kPrintDebug 1

// Creates a CardNode to be implemented in the creation of a new hand.
// Returns a pointer to the CardNode.
CardNode* CreateCardNode(Card* card, CardNode* prev, CardNode* next) {
  CardNode* cardnode = (CardNode*)malloc(sizeof(CardNode));
  cardnode->this_card = card;
  cardnode->prev_card = prev;
  cardnode->next_card = next;
  return cardnode;
}

// Creates a Hand struct and initializes any necessary fields.
// Returns a pointer to the new hand, which has been allocated to the heap.
Hand* CreateHand() {
  Hand* hand = (Hand*)malloc(sizeof(Hand));
  if (hand == NULL) {
    printf("Malloc failed for creating hand");
    return NULL;
  }
  hand->first_card = NULL;
  hand->num_cards_in_hand = 0;
  return hand;
}

// Adds a card to the hand.
void AddCardToHand(Card* card_ptr, Hand* hand_ptr) {
  CardNode* card_to_add = CreateCardNode(card_ptr, NULL, NULL);
  if (IsHandEmpty(hand_ptr) == 1) {
    hand_ptr->first_card = card_to_add;
  } else {
    card_to_add->next_card = hand_ptr->first_card;
    hand_ptr->first_card->prev_card = card_to_add;
    hand_ptr->first_card = card_to_add;
  }
  hand_ptr->num_cards_in_hand++;
}

// Removes a card from the hand.
// Does not free the card; it is the responsibility
// of the caller to free the card at the appopriate
// time.
// Returns a pointer to the card that is no longer in the hand.
Card* RemoveCardFromHand(Card* card_ptr, Hand* hand_ptr) {
  if (IsHandEmpty(hand_ptr) == 0) {
    CardNode* temp = hand_ptr->first_card;
    while (temp != NULL) {
      if (temp->this_card == card_ptr) {
        if (hand_ptr->num_cards_in_hand == 1) {
          hand_ptr->first_card = NULL;
        } else if (temp == hand_ptr->first_card) {
          hand_ptr->first_card = hand_ptr->first_card->next_card;
          hand_ptr->first_card->prev_card = NULL;
        } else if (temp->next_card == NULL) {
          temp->prev_card->next_card = NULL;
        } else {
          temp->prev_card->next_card = temp->next_card;
          temp->next_card->prev_card = temp->prev_card;
        }
        hand_ptr->num_cards_in_hand--;
        free((void*)temp);
        return card_ptr;
      }
      temp = temp->next_card;
    }
  }
  return NULL;
}

// Determines if there are any cards in the hand.
// Return 1 if the hand is empty, 0 otherwise.
int IsHandEmpty(Hand* hand_ptr) {
  if (hand_ptr->num_cards_in_hand == 0) {
    return 1;
  }
  return 0;
}

// Destroys the hand, freeing any memory allocated for it.
void DestroyHand(Hand* hand_ptr) {
  if (IsHandEmpty(hand_ptr) == 1) {
    free(hand_ptr->first_card);
    free(hand_ptr);
  }
}

// Helper function which determines if a hand contains a card of
// the given suit.
// Will return 1 if there is a card in the deck of the given suit,
// 0 otherwise.
int HasCardsOfSuit(Suit suit, Hand* hand) {
  CardNode* temp = hand->first_card;
  while (temp != NULL) {
    if (temp->this_card->suit == suit) {
      return 1;
    } else {
      temp = temp->next_card;
    }
  }
  return 0;
}

// Given a lead card, a player's hand, and the card the player
// wants to play, is it legal?
// If the play has a card of the same suit as the lead card,
// they must play a card of the same suit.
// If the player does not have a card of the same suit, they can
// play any card.
// Returns 1 if the move is legal, 0 otherwise.
int IsLegalMove(Hand* hand_ptr, Card* lead_card, Card* played_card) {
  if (HasCardsOfSuit(lead_card->suit, hand_ptr) > 0) {
    if (played_card->suit != lead_card->suit) {
      return 0;
    }
  }
  return 1;
}

// Helper function that determines the value of the card.
int DetermineValue(Name name) {
  switch (name) {
  case NINE: return 0;
  case TEN: return 1;
  case JACK: return 2;
  case QUEEN: return 3;
  case KING: return 4;
  case ACE: return 5;
  default: return 0;
  }
}

// Given two cards that are played in a hand, which one wins?
// If they suits are the same, the higher card value wins.
// If the suits are not the same, player 1 wins, unless player 2 played trump.
// Returns 1 if the person who led won, 0 if the person who followed won.
int WhoWon(Card* lead_card, Card* followed_card, Suit trump) {
  if (lead_card->suit == followed_card->suit) {
    lead_card->value = DetermineValue(lead_card->name);
    followed_card->value = DetermineValue(followed_card->name);
    if (lead_card->value > followed_card->value) {
      return 1;
    } else {
      return 0;
    }
  } else if (followed_card->suit == trump) {
    return 0;
  } else {
    return 1;
  }
}
