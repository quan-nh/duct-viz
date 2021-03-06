# duct-viz
[![Clojars Project](https://img.shields.io/clojars/v/duct-viz.svg)](https://clojars.org/duct-viz)

A Leiningen plugin to present a dependency diagram of [Duct framework](https://github.com/duct-framework/duct) based app, using [Graphviz](http://www.graphviz.org/).

## Usage

Put `[duct-viz "0.1.3"]` into the `:plugins` vector of your project.clj.

```bash
$ lein duct-viz -h
Usage: lein duct-viz [options]
    
Options:
  -c, --config-file CONFIG_FILE  config.edn  Duct config file.
  -d, --dev                                  Use :dev profile.
  -o, --output-file OUTPUT_FILE  system.png  Output file path, png image.
  -v, --vertical                             Use vertical layout.
  -h, --help                                 Help.
```

Here's an example of a project using `lein new duct foo +api +ataraxy +example +sqlite` template:

![system](system.png)

## License

Copyright © 2017 Quan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
