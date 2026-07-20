{
  description = "MediPro - 8番出口パロディゲーム";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
      in
      {
        packages.default = pkgs.stdenv.mkDerivation {
          pname = "medipro";
          version = "0.1.0";
          src = ./.;

          nativeBuildInputs = with pkgs; [ jdk17 makeWrapper ];

          buildPhase = ''
            mkdir -p classes bin
            javac -encoding UTF-8 --release 17 -sourcepath src -d classes src/medipro/Main.java
            mv "img/コンクリ壁_grid.png" img/concrete_grid.png
            jar cfm bin/app.jar manifest.txt -C classes . -C src medipro img
          '';

          installPhase = ''
            mkdir -p $out/bin $out/share/java
            cp bin/app.jar $out/share/java/medipro.jar
            makeWrapper ${pkgs.jdk17}/bin/java $out/bin/medipro \
              --add-flags "-jar $out/share/java/medipro.jar"
          '';
        };

        apps.default = {
          type = "app";
          program = "${self.packages.${system}.default}/bin/medipro";
        };

        devShells.default = pkgs.mkShell {
          packages = with pkgs; [
            jdk17
            checkstyle
            clang-tools
            graphviz
          ];
        };
      }
    );
}
