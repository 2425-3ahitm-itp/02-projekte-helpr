{
  description = "Helpr";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/3730d8a3";
  inputs.flake-utils.url = "github:numtide/flake-utils";

  outputs = { self, nixpkgs, flake-utils }: flake-utils.lib.eachDefaultSystem (system:
    let
      pkgs = import nixpkgs { inherit system; };
    in {
      devShells.default = pkgs.mkShell {
        buildInputs = [
          pkgs.go_1_23
          pkgs.hugo
          pkgs.quarkus
          pkgs.maven
          pkgs.jdk21_headless
          pkgs.docker_28
          pkgs.docker-buildx
          pkgs.docker-compose
        ];
      };
    }
  );
}
