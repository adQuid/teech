package serialize

object Serializer {

    fun mapFromJsonString(string: String): Map<String, Any>{
        val withoutCurlys = string.subSequence(1, string.length-1)

        val entries = topLevelComponents(withoutCurlys.toString(), ',')

        return entries.map{ topLevelComponents(it, ':')}.associate { convertStrictlyToString(trimMisc(it[0]) ) to convertToObjectType(trimMisc(it[1])) }
    }

    private fun topLevelComponents(str: String, seperator: Char): List<String>{
        var curlyCount = 0
        var bracketCount = 0
        var inQuotes = false

        var retval = mutableListOf<String>()
        var buffer = ""

        str.forEachIndexed { index, it ->

            if(it == '"'){
                inQuotes = !inQuotes
            }

            if(it == '{'){
                curlyCount++
            }
            if(it == '}'){
                curlyCount--
            }
            if(it == '['){
                bracketCount++
            }
            if(it == ']'){
                bracketCount--
            }

            if(!inQuotes && curlyCount == 0 && bracketCount == 0
                    && it == seperator){
                retval.add(buffer)
                buffer = ""
            } else {
                buffer += it
            }
        }

        //last section isn't delimited
        if(buffer != ""){
            retval.add(buffer)
        }
        return retval
    }

    fun convertToObjectType(input: String): Any{
        if(input.first() == '\"' && input.last() == '\"'){
            return convertStrictlyToString(input)
        }
        if(input == "true"){
            return true
        }
        if(input == "false"){
            return false
        }
        if(input.matches("-?\\d+(\\.\\d+)?".toRegex())){
            return input.toDouble()
        }
        if(input.first() == '{' && input.last() == '}'){
            return mapFromJsonString(input)
        }
        if(input.first() == '[' && input.last() == ']'){
            return topLevelComponents(input.subSequence(1, input.length-1).toString(), ',').map{ convertToObjectType(trimMisc(it))}
        }
        throw Exception("unrecognized data type: $input!")
    }

    fun convertStrictlyToString(input: String): String{
        if(input.first() == '\"' && input.last() == '\"'){
            return input.subSequence(1, input.length-1).toString()
        }
        throw Exception("Invalid string: $input!")
    }

    private fun trimMisc(input: String): String{
       return input.replace("\n", "").replace("\r", "").trim()
    }
}