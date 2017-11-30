# JFrameTester
## Running
The built jar can be found in `build/libs/jframetester.jar`

### Usage
`java -jar jframetester.jar [options] <name of JFRAME>`

#### Ex.
`java -jar jframetester.jar -i build/demo/zips -o build/demo/pngs -c CircleFrame`

### Options
<table>
    <tr>
        <th>Switch</th>
        <th>Long switch</th>
        <th>Description</th>
        <th>Default</th>
    </tr>
    <tr>
        <td>-h</td>
        <td>--help</td>
        <td>Print help and exit</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>-i</td>
        <td>--input</td>
        <td>Specify the input zip directory</td>
        <td>pwd</td>
    </tr>
    <tr>
        <td>-o</td>
        <td>--output</td>
        <td>Specify the output png directory</td>
        <td>$HOME/jframetests</td>
    </tr>
    <tr>
        <td>-c</td>
        <td>--compile</td>
        <td>If present, will compile .java if they're not compiled</td>
        <td>false</td>
    </tr>
</table>

## Bundled Libraries
* [picocli](https://github.com/remkop/picocli) under the Apache License
* [zip4j](http://www.lingala.net/zip4j/?zip4j) under the Apache License