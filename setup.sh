#!/bin/bash

SESH="currency-converter"

tmux has-session -t $SESH 2>/dev/null

if [ $? != 0 ]; then
  tmux new-session -d -s $SESH -n "neovim"
  tmux send-keys -t $SESH:neovim "nvim src/main/java/com/example/currencyconverter/CurrencyConverter.java" C-m

  tmux new-window -t $SESH -n "run"
  tmux send-keys -t $SESH:run "clear" C-m

  tmux new-window -t $SESH -n "test"
  tmux send-keys -t $SESH:run "clear" C-m

  tmux select-window -t $SESH:neovim
fi

tmux attach-session -t $SESH
