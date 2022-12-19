# Json Logic - Proof of Concept

Application is processing input data by checking rules externalized in JSON file.

Rules configs are stored in the config files `config.json` in `alert-rules` directory, example rule:
```JSON
{ "<": [
    { "/": [
        {"var": "savings"},
        {"var": "brokerage"}
    ]},
    0.25
]}
```

Input data is stored in `fund_balance.json`, example value:
```JSON
{
  "personal": "5000",
  "secondPersonal": "5000",
  "savings": "500",
  "brokerage": "9500",
  "currency": "pln",
  "operator": "pko-bp"
}
```

For each input data we are concatenating rules from three files:
* `alert-rules/config.json`, 
* `alert-rules/${currency}/config.json`,
* `alert-rules/${currency}/${operator}/config.json`

### Tech Details

For processing rules we are using [json-logic](https://jsonlogic.com/) standard defined in the library: [json-logic-java](https://github.com/jamsesso/json-logic-java) .

Library already defined several dozen of operators, but allow also adding new ones.

Example naive implementation of new operation: `abs` standing for `absolut value`
```
  private val abs: Array[AnyRef] => Double = args => Math.abs(args.head.asInstanceOf[Double])
  val jsonLogic: JsonLogic =
    new JsonLogic()
      .addOperation("abs", abs.asInstanceOf[Array[AnyRef] => AnyRef].asJavaFunction)
```


### Example scenario:

1. Run `App.scala`. It should print: `saving account balance to brokerage account balance ratio is too low`
2. Transfer `2000` from `brokerage` to `savings` (set `savings` as `2500` and `brokerage` as `7500`) and run again. It should print no alerts!
3. New income is coming, we can see it in `personal` (set `personal` as `15000`) but we want to keep both personal accounts with the similar balance. Run app. It should print `personal accounts difference warning` and `personal accounts difference critical`
4. Transfer `12000` to saving accounts (set `hot` as `2000`). Run app. It should print: `hot balance funds exceed safe values`
5. Having split configuration per currency and per operator allow us to have different rules, e.g. change in input currency to `btc` and no alert should be visible.
