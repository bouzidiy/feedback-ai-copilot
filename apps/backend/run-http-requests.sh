#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REQUEST_FILE="${REQUEST_FILE:-$SCRIPT_DIR/http-requests.txt}"
LAST_FEEDBACK_ID=""

replace_placeholders() {
  local value="$1"
  value="${value//\{\{feedbackId\}\}/$LAST_FEEDBACK_ID}"
  printf '%s' "$value"
}

run_request() {
  local -a lines=("$@")
  local method=""
  local path=""
  local -a args=()
  local response=""
  local id=""

  for line in "${lines[@]}"; do
    [[ -z "$line" || "$line" =~ ^[[:space:]]*# ]] && continue

    if [[ -z "$method" ]]; then
      read -r method path <<< "$line"
      path="$(replace_placeholders "$path")"
    else
      args+=("$(replace_placeholders "$line")")
    fi
  done

  [[ -z "$method" ]] && return

  echo "Running $method $path"
  response="$(http --ignore-stdin "$method" "$BASE_URL$path" "${args[@]}")"
  echo "$response"

  id="$(echo "$response" | sed -nE 's/^[[:space:]]*"id"[[:space:]]*:[[:space:]]*"([^"]+)".*/\1/p' | head -1)"
  if [[ -n "$id" ]]; then
    LAST_FEEDBACK_ID="$id"
  fi
}

if [[ ! -f "$REQUEST_FILE" ]]; then
  echo "Request file not found: $REQUEST_FILE" >&2
  exit 1
fi

block=()
while IFS= read -r line || [[ -n "$line" ]]; do
  if [[ -z "$line" ]]; then
    run_request "${block[@]}"
    block=()
  else
    block+=("$line")
  fi
done < "$REQUEST_FILE"

run_request "${block[@]}"
