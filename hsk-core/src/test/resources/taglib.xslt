<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:cc="http://java.sun.com/jsf/composite" xmlns="http://java.sun.com/xml/ns/javaee" version="2.0">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:param name="URL"/>
    <xsl:variable name="POINT" select="'.'"/>
    <xsl:template match="/">
        <facelet-taglib xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facelettaglibrary_2_0.xsd" version="2.0">
            <namespace>
                <xsl:value-of select="$URL"/>
            </namespace>
            <xsl:apply-templates/>
        </facelet-taglib>
    </xsl:template>
    <xsl:template match="cc:components">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="cc:component">
        <xsl:comment>
            <xsl:value-of select="@fileName"/>
        </xsl:comment>
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="cc:interface">
        <tag>
            <description>
                <xsl:value-of select="@shortDescription"/>
                <xsl:value-of select="$POINT"/>
            </description>
            <tag-name>
                <xsl:value-of select="../@name"/>
            </tag-name>
            <component>
                <resource-id>
                    <xsl:value-of select="../@resourceId"/>
                </resource-id>
            </component>
            <xsl:apply-templates/>
        </tag>
    </xsl:template>
    <xsl:template match="cc:attribute">
        <attribute>
            <description>
                <xsl:value-of select="@shortDescription"/>
                <xsl:value-of select="$POINT"/>
            </description>
            <name>
                <xsl:value-of select="@name"/>
            </name>
            <required>
                <xsl:choose>
                    <xsl:when test="@required != ''">
                        <xsl:value-of select="@required"/>
                    </xsl:when>
                    <xsl:otherwise>false</xsl:otherwise>
                </xsl:choose>
            </required>
            <type>
                <xsl:choose>
                    <xsl:when test="@type != ''">
                        <xsl:value-of select="@type"/>
                    </xsl:when>
                    <xsl:otherwise>java.lang.String</xsl:otherwise>
                </xsl:choose>
            </type>
            <default>
                <xsl:value-of select="@default"/>
            </default>
        </attribute>
    </xsl:template>
    <xsl:template match="@*|node()"/>
</xsl:transform>
