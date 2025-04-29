#!/bin/bash

# Environment variables
EMOJI_FEAT="✨"
EMOJI_FIX="🐛"
EMOJI_DOCS="📚"
EMOJI_REFACTOR="♻️"
EMOJI_TEST="✅"
EMOJI_PERF="🚀"
EMOJI_STYLE="🎨"
EMOJI_CHORE="🔧"
EMOJI_BUILD="🏗️"
EMOJI_CI="🧪"
EMOJI_REVERT="⏪️"
EMOJI_SECURITY="🔒️"
EMOJI_RELEASE="📦"
EMOJI_CLEANUP="🧹"
EMOJI_MERGE="🔀"
GITHUB_BASE_LINK="https://github.com/2425-3ahitm-itp/02-projekte-helpr/commit"
ISSUE_BASE_LINK="https://vm81.htl-leonding.ac.at/issue"

# Format for AUTHOR_ALIASES:
# "alias1|alias2=MainName;alias3|alias4=MainName2"
# Use semicolons to separate different author mappings
# Use pipes to separate aliases for the same author
AUTHOR_ALIASES="J.H.F.|Jakob Huemer-Fistelberger=JakobHuemer;Marlies Winklbauer=MarliesWkbr;root|miriam|Miriam Gnadlinger=mGnadlinger;--global|Sperrer Simone=SimoneSperrer;Tom=ThomasDieLock"

# Process author aliases
process_aliases() {
  local name="$1"
  local result="$name"  # Default to original name

  # Loop through each mapping (using semicolon as delimiter between mappings)
  IFS=';' read -ra mappings <<< "$AUTHOR_ALIASES"
  for mapping in "${mappings[@]}"; do
    # Extract main name
    main_name=$(echo "$mapping" | cut -d'=' -f2)
    # Extract aliases
    aliases=$(echo "$mapping" | cut -d'=' -f1)

    # Check if name matches any alias
    IFS='|' read -ra alias_array <<< "$aliases"
    for alias in "${alias_array[@]}"; do
      if [ "$name" = "$alias" ]; then
        result="$main_name"
        break 2  # Exit both loops
      fi
    done
  done

  echo "$result"
}

# Determine emoji based on commit message
get_emoji() {
  local message="$1"
  local lowercase_message=$(echo "$message" | tr '[:upper:]' '[:lower:]')

  # Simple case statement for compatibility with basic shells
  case "$lowercase_message" in
    *merge*)
      echo "$EMOJI_MERGE" ;;
    *feat*|*feature*|*new*)
      echo "$EMOJI_FEAT" ;;
    *fix*|*bug*|*issue*)
      echo "$EMOJI_FIX" ;;
    *doc*|*docs*|*documentation*)
      echo "$EMOJI_DOCS" ;;
    *refactor*)
      echo "$EMOJI_REFACTOR" ;;
    *test*)
      echo "$EMOJI_TEST" ;;
    *perf*|*performance*)
      echo "$EMOJI_PERF" ;;
    *style*)
      echo "$EMOJI_STYLE" ;;
    *chore*)
      echo "$EMOJI_CHORE" ;;
    *build*)
      echo "$EMOJI_BUILD" ;;
    *ci*)
      echo "$EMOJI_CI" ;;
    *revert*)
      echo "$EMOJI_REVERT" ;;
    *security*)
      echo "$EMOJI_SECURITY" ;;
    *release*)
      echo "$EMOJI_RELEASE" ;;
    *cleanup*)
      echo "$EMOJI_CLEANUP" ;;
    *)
      echo "$EMOJI_FEAT" ;;  # Default to feature
  esac
}

# Replace issue tags with links
replace_issue_tags() {
  local message="$1"
  # Use different delimiter for sed (| instead of /) to avoid conflicts with URLs
  # Remove the # when creating the link
  echo "$message" | sed -E "s|(#helpr-[0-9]+)|[\1]($ISSUE_BASE_LINK/helpr-\1)|g" | sed 's|/helpr-#helpr-|/helpr-|g'
}

# Format conventional commit prefixes with markdown code blocks
format_commit_prefixes() {
  local message="$1"
  # Match the most common conventional commit prefixes followed by a colon
  # The prefixes include: feat, fix, docs, style, refactor, perf, test, build, ci, chore, revert, security, release, cleanup
  # First handle Merge prefixes with bold formatting
  message=$(echo "$message" | sed -E 's/^(Merge)/\*\*\1\*\*/gi')
  # Then handle conventional commit prefixes
  echo "$message" | sed -E 's/^(feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert|security|release|cleanup):/`\1` /gi'
}

# Main processing
process_changelog() {
  local input_file="$1"
  local temp_file=$(mktemp)

  # Map aliases and store in temporary file
  while IFS= read -r line; do
    # Get the first two semicolons to properly extract author, hash, and message
    author=$(echo "$line" | cut -d';' -f1)
    hash=$(echo "$line" | cut -d';' -f2)
    # Everything after the second semicolon is the message
    message=$(echo "$line" | cut -d';' -f3-)

    # Get the main author name
    main_author=$(process_aliases "$author")
    # Get the emoji
    emoji=$(get_emoji "$message")
    # Format conventional commit prefixes
    formatted_prefix_message=$(format_commit_prefixes "$message")
    # Replace issue tags
    formatted_message=$(replace_issue_tags "$formatted_prefix_message")

    echo "$main_author;$hash;$formatted_message;$emoji" >> "$temp_file"
  done < "$input_file"

  # Sort by author
  sort -t';' -k1,1 "$temp_file" > "${temp_file}.sorted"

  # Generate the markdown output
  current_author=""
  author_commit_count=0

  while IFS=';' read -r author hash message emoji; do
    if [ "$author" != "$current_author" ]; then
      # Output previous author's section
      if [ -n "$current_author" ]; then
        echo "" # Add a blank line between sections
      fi

      # Start new author section
      echo "## $author"

      # Count commits for this author
      author_commit_count=$(grep "^$author;" "${temp_file}.sorted" | wc -l)
      echo "📦 $author_commit_count commits"
      echo ""

      current_author="$author"
    fi

    # Output the commit line
    echo "- $emoji $message [\`${hash:0:7}\`]($GITHUB_BASE_LINK/$hash)"
  done < "${temp_file}.sorted"

  # Clean up temp files
  rm "$temp_file" "${temp_file}.sorted"
}

# Check if input is from stdin or file
if [ -t 0 ]; then
  # Terminal input, expecting a file argument
  if [ $# -eq 0 ]; then
    echo "Usage: $0 <changelog_file>"
    exit 1
  fi

  process_changelog "$1"
else
  # Input from pipe
  temp_input=$(mktemp)
  cat > "$temp_input"
  process_changelog "$temp_input"
  rm "$temp_input"
fi