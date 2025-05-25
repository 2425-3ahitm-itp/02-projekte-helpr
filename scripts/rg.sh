#!/usr/bin/env bash

set -e

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
EMOJI_GENERIC="🧩"

GITHUB_BASE_LINK="https://github.com/2425-3ahitm-itp/02-projekte-helpr/commit"
ISSUE_BASE_LINK="https://vm81.htl-leonding.ac.at/issue"

# example content:
# JakobHuemer;a5a9124;1740160705;Merge task/helpr-12-task-repository
# MarliesWkbr;f94e3c5;1740087535;feat(taskRepository): CRUD Methoden angelegt #helpr-12
# miriam;1989e72;1742667806;fix: undelete lines
# J.H.F.;6136589;1748183983;Merge pull request #20 from 2425-3ahitm-itp/dev
# mGnadlinger;c4be2e9;1740084066;Fix: fixing DatasourceProviderTest: wrong checking #helpr-15-datasource-provider Done
# SimoneSperrer;59de0ec;1740065854;Merge task/helpr-11 #helpr-11 Done
# --global;5391fdb;1739812380;feat(task-model): init3 #helpr-11 In Progress
# Sperrer Simone;d41bac4;1739803802;feat(task-model): init #helpr-11 In Progress
# root;b6bb273;1738782581;chow: add quarkus extension qurkus-jdbc-postsql #helpr-15 In Progress
# Marlies Winklbauer;43ef963;1745701972;refactor: paymentfilter to effort filter, postalcodefilter to locationfilter #helpr-30
# Jakob Huemer-Fistelberger;e12098d;1738770461;feat(datamodel): create schema.sql from plantuml datamodel #helpr-9 To Verify
# mGnadlinger;ed90b14;1733172782;Update pflichtenheft - verbesserte Formulierung und bessere Lesbarkeit

# region get input ------------------------
input="${1:-/dev/stdin}"
[[ -r "$input" ]] || { echo "Cannot read input: $input" >&2; exit 1; }
content=$(< "$input")
# endregion


# region functions -----------------------------------
function handle_commit_message() {
  local message="$1"
  local commit_hash="$2"

  # emojis

  # generic conventional commit
  message=$(echo "$message" | perl -pe 's/^(feat|fix|docs|refactor|test|perf|style|chore|build|ci|revert|security|release|cleanup)(\([a-zA-Z_-]+\))?:/`\1\2`/i')

  shopt -s nocasematch
  # add emojies based on commit type case-insensitive

  if [[ "$message" =~ ^\`feat(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_FEAT} ${message}"
  elif [[ "$message" =~ ^\`fix(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_FIX} ${message}"
  elif [[ "$message" =~ ^\`docs(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_DOCS} ${message}"
  elif [[ "$message" =~ ^\`refactor(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_REFACTOR} ${message}"
  elif [[ "$message" =~ ^\`test(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_TEST} ${message}"
  elif [[ "$message" =~ ^\`perf(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_PERF} ${message}"
  elif [[ "$message" =~ ^\`style(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_STYLE} ${message}"
  elif [[ "$message" =~ ^\`chore(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_CHORE} ${message}"
  elif [[ "$message" =~ ^\`build(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_BUILD} ${message}"
  elif [[ "$message" =~ ^\`ci(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_CI} ${message}"
  elif [[ "$message" =~ ^\`revert(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_REVERT} ${message}"
  elif [[ "$message" =~ ^\`security(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_SECURITY} ${message}"
  elif [[ "$message" =~ ^\`release(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_RELEASE} ${message}"
  elif [[ "$message" =~ ^\`cleanup(\([a-zA-Z0-9_-]+\))?\` ]]; then
    message="${EMOJI_CLEANUP} ${message}"
  elif [[ "$message" =~ ^merge* ]]; then
    message="${EMOJI_MERGE} **Merge**${message:5}"
  else
    # default emoji for generic commit messages
    message="${EMOJI_GENERIC} ${message}"
  fi

  # replace "#helpr-<nr>" with "[#helpr-<nr>](${ISSUE_BASE_LINK}/helpr-<nr>)"
  message="$(echo "$message" | sed -E "s/#helpr-([0-9]+)/[#helpr-\1](${ISSUE_BASE_LINK//\//\\/}\/helpr-\1)/g")"

  # append commit hash as link
  if [[ -n "$commit_hash" ]]; then
    message="${message} [\`$commit_hash\`](${GITHUB_BASE_LINK}/$commit_hash)"
  fi

  echo "$message"
}

# endregion


# region aliases and replace --------------------------
declare -A aliases

dict="
JakobHuemer:J.H.F.|Jakob Huemer-Fistelberger
mGnadlinger:root|miriam|Miriam Gnadlinger
SimoneSperrer:--global|Sperrer Simone
MarliesWkbr:Marlies Winklbauer
"

# Parse Aliases
while IFS=":" read -r key raw_aliases; do
    IFS="|" read -ra alias_array <<< "$raw_aliases"
    for alias in "${alias_array[@]}"; do
        trimmed=$(echo "$alias" | xargs)
        [[ -n "$trimmed" ]] && aliases["$trimmed"]="$key"
    done
done <<< "$dict"

# iterate over aliases
for alias in "${!aliases[@]}"; do
#  echo "$alias -> ${aliases[$alias]}"
  content=$(echo "$content" | sed -E "s/^($alias);/${aliases[$alias]};/")
done
# endregion


# region sorting -------------------------

# first sort by date and then by author
content=$(echo "$content" | sort -t ';' -k3,3nr | sort -t ';' -k1,1 -s)

# region parsing

# extract the commit message (4th and last column)
# parse it and change it
# put it back and replace the old one

# Extract the commit message (4th column)
parsed_content=""
while IFS=";" read -r author hash date message || [ -n "$author" ]; do
    if [[ -z "$author" ]]; then
        continue
    fi

    # Here you can edit the commit message
    commit_message="$message"
    # TODO: Add your commit message parsing/editing logic here
    # use handle_commit_message on commit message
    commit_message="$(handle_commit_message "$commit_message" "$hash")"

    # Put it back together
    parsed_line="$author;$commit_message"

    if [[ -z "$parsed_content" ]]; then
        parsed_content="$parsed_line"
    else
        parsed_content+=$'\n'"$parsed_line"
    fi
done <<< "$content"

# Replace the original content with parsed content
content="$parsed_content"

# endregion


#printf "$content"


# region group commits by author in array ------------------------------
declare -a author_groups
current_author=""
current_group=""

while IFS=";" read -r author message || [ -n "$author" ]; do
    if [[ -z "$author" ]]; then
        continue
    fi

    if [[ "$author" != "$current_author" && -n "$current_group" ]]; then
        # New author encountered, add the completed group to the array
        author_groups+=("$current_group")
        current_group=""
    fi

    current_author="$author"
    if [[ -z "$current_group" ]]; then
        current_group="$author;$message"
    else
        current_group+=$'\n'"$author;$message"
    fi
done <<< "$content"

# Add the last group if it exists
if [[ -n "$current_group" ]]; then
    author_groups+=("$current_group")
fi

# Print all author groups
for group in "${author_groups[@]}"; do
  # each line in group is "author;message"
  # new group content:
  # ## <author>
  # 📦 <nr> commits
  #
  # - <message1>
  # - <message2>

  IFS=$'\n' read -r -d '' -a lines < <(printf '%s\0' "$group")
  author="${lines[0]%%;*}"
  commit_count=${#lines[@]}
  echo "## $author"
  echo "📦 $commit_count commits"
  echo ""
  for line in "${lines[@]}"; do
    if [[ -z "$line" ]]; then
      continue
    fi
    message="${line#*;}"  # Remove the author part
    echo "- $message"
  done
  echo ""  # Add a blank line after each author's section

done

# endregion
