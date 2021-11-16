# hsk-core
HSK Core Library

* hsk
    * hsk-core      All Source
    * hsk-result    Source for Model Basic
    * hsk-faces     Source for JSF
    * hsk-jaxrs     Source for REST-API

## Result
The lib has a *basic model* for manipule:
- Query Filter 
- Query Pagination
- Query Sorting
- Result Response
- Result Messsage
- Result Structure
- Result Exception for Message
- Param Domain
- Param Instance  ( value - label )
- Param Options   ( value - label - children)

### Result Builder
The lib include a build object for:
- FilterBuilder for create a JPA Query from:
  - Filter  instance elements
  - Sortable   instance
  - Pagination instance
- MessageBuilder for create a Message, include:
  - code message
  - descripcion
  - list of causes
  - list ot traces
  - converte to/from MessageException
- DomainBuilder for create a set of domain-value request
  - create a MapList of domain, classification domains
- ResultBuilder for create a Basic response, include the fields:
  - Flag success and error
  - Value data as List, Object or NONE
  - List of Messages


