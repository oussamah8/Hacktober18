import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.css.CSSCharsetRule;

public class ExtractionClose {
	static int N=5,M=5;
 static ArrayList<String> Fermetures=new ArrayList<String>();
 static ArrayList<String> Items=new ArrayList<String>();
  static  int data[][]=new int[N][M];
  static ArrayList<Boolean> attributs=new ArrayList<Boolean>();
  static int longu=1,minSup=2;
//Charset
private static char[] _charset;
//Longueur max
private static Integer _longueur ;
 
private static Integer maxCombi (Integer charsetsize, Integer longueur) 
{
    // Variable qui cumule les combinaisons possibles (par défaut = 0)
    Double max = 0.0;

    // Cumul pour toutes les longueurs possibles
    for (int j=1;j<=longueur;j++) 
   {
        max += Math.pow((double)charsetsize, (double)j);
    }

    return max.intValue();
}



private static String computeMot (Integer charsetsize, Integer indice,Integer k) 
{
    // Mot à retourner (par défaut vide)
    String result = "";

    // Calcul du mot
    while (indice>=0) {
    	boolean trouv=true;
    	for (int i=0; i<result.length(); i++)
    	{
    		if(_charset[(indice%charsetsize)]==result.charAt(i)){ trouv=false; result=""; }
    	}
        if (trouv) 
        	{  
        	 if(attributs.get(Character.getNumericValue(_charset[(indice%charsetsize)]))!=false )
        	 result = _charset[(indice%charsetsize)] + result;
        	
        	
        	}
        
         indice = (indice/charsetsize) - 1;
         if(result.length()>longu) longu=result.length();
    }
    boolean trouv=false; 
    if (result.length() >=longu ) 
    { 
         int i=0; boolean trouver=false;
         while(i<Items.size() && trouver==false)
         {
        	 if(Items.get(i).length()==result.length())
        	 {
        		 char[] ar = result.toCharArray();
        		 Arrays.sort(ar);
        		 String sortedResult = String.valueOf(ar);
        		 
        		 char[] ar2 = Items.get(i).toCharArray();
        		 Arrays.sort(ar2);
        		 String sortedItem = String.valueOf(ar2);
        		 
        		 if(sortedItem.equals(sortedResult))
        		 {
        			 trouver=true;
        		 }
        		 
        	  }
        	 i++;
         }
         if(trouver==false) {
        	 
        	 boolean trouvegeneral=false,trouveT=true,trouvlettre; int par=0;
        	
        	 while(par<Fermetures.size() && trouvegeneral==false)
             {int i1=0,cpt=0;  trouveT=true;
                  
             while(i1<result.length() && trouveT==true)
             { 
             	int j=0; trouvlettre=false;
             	while(j<Fermetures.get(par).length() && trouvlettre==false)
             	{  
             		if(result.charAt(i1)==Fermetures.get(par).charAt(j)) 
             			{  
             				trouvlettre=true; 
             				cpt++;
             			}
             		j++;
             		
             	}

             	if(trouvlettre==true) i1++;
             	if(trouvlettre==false) 
             	{   trouveT=false;
             	}
        
             }
            
               if(trouveT==true) 
               {  if(cpt==result.length())
                   {   
            	     	
            	     	trouvegeneral=true;
                    }
               }
             par++;

         }
        	if(trouvegeneral==false)  
        	{   if(result.length()==k )Items.add(result); 

        	}
         }
         else result="";
         return result;
         }
         
	
	
    
	 
       
	  
    else return "";
}

  
  
  
  
  
  
  
  
  
  
  //remplir la matrice du dataFile
  static void readData()
  {   String chars="";
    _longueur=M; System.out.print("|");
      for(int i=0; i<M; i++)
      {  System.out.print(i+"|");
    	  chars+=String.valueOf(i);
      }
      System.out.print("\n");
      for(int i=0; i<M; i++)
      {  System.out.print("--");
    	  
      } 
      _charset=chars.toCharArray();
          
	  BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader("data.txt");
			br = new BufferedReader(fr);

			String sCurrentLine;
                int i=0;
			while ((sCurrentLine = br.readLine()) != null) {
					String[] tab=sCurrentLine.split(" ");
					if(i<N)
				for(int j=0; j<M; j++)
				{  
					data[i][j]=Integer.parseInt(tab[j]);

				}
				
				i++;
				
				
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println();
	  for (int i=0; i<N; i++)
	  {
		  for(int j=0; j<M; j++)
			  System.out.print(" "+data[i][j]);
		  System.out.println();
	  }
	  
		 System.out.println("\nminSup="+minSup);

	  
  }
  
//construit les k-items
 static    ArrayList<String> ConstruireItem(int k)
   {  ArrayList<String> Kitems=new ArrayList<String>();

	// Nombre de combinaisons possibles pour le charset _charset et la longueur _longueur définie plus haut
	Integer nbCombinaison = maxCombi(_charset.length, _longueur);

       // Mot composé par la methode computeMot
       String mot = "";

       // Iteration sur la méthode computeMot
       for (int i=0;i<nbCombinaison;i++) 
       { 
           mot = computeMot (_charset.length,i,k);
           if(mot.length()>k) i=nbCombinaison;
       }
       System.out.println("\nles "+k+"-items sont: ");
       for (int i=0;i<Items.size();i++) 
       {
          if(Items.get(i).length()==k)
          {  Kitems.add(Items.get(i));
  		     

             System.out.print(" "+Items.get(i));
            
          }
       }
          
          for (int i=0; i<Kitems.size(); i++)
          { 
  		     System.out.println();
                 Fermeture(Kitems.get(i));
            
          }       
       
	   return Kitems;
   }
 
 
   //fermeture d'un itemset
   static void Fermeture( String item)
   { 
	   ArrayList appar=ApparItem(item);
		ArrayList<Integer> Ferm=new ArrayList<Integer>();
		
		//la premiere ligne ou il apparait l'item
       for(int i=0; i<M; i++)	
       { if(appar.size()>0)
       	if(data[(int) appar.get(0)][i]==1 ) 
       	{   //ajouter les fermés de la premiere ligne ou apparait l'item
       		Ferm.add(i); 
       	}
       	
       }
       //parccourir les autres lignes ou apparait l'item
       for(int k=1; k<appar.size(); k++)
       {
    	  int j=0;  
    	   for (j=0; j<Ferm.size(); j++)
    	   {
    		 if (data[(int) appar.get(k)][Ferm.get(j)]==0) 
    		  { 
    			 Ferm.remove(j);   }
    	   }
       	 
       }
       
       String  Fermé="";
       for(int k=0; k<Ferm.size(); k++)
       {  Fermé+=String.valueOf(Ferm.get(k));
    	   
       }
       
       char[] it =Fermé.toCharArray();
		 Arrays.sort(it);
		 String sortedFermé = String.valueOf(it);
		 
		   char[] it1 =item.toCharArray();
			 Arrays.sort(it1);
			 String sortedItem = String.valueOf(it1);
			 boolean cont=false;
      for(int i=0; i<item.length(); i++)
       { if(!Fermé.contains( new Character(item.charAt(i)).toString()))
    	   
    	   Ferm.add(Character.getNumericValue(item.charAt(i)));
           Fermé+=item.charAt(i);
       } 
        System.out.print("\nla fermeture de "+item+"est :");
       String Fermeture="";
       for(int k=0; k<Ferm.size(); k++)
       {      Fermeture+=String.valueOf(Ferm.get((k))) ;
    	   System.out.print(" "+Ferm.get(k));

    	}
       if(!Fermetures.contains(Fermeture))
       Fermetures.add(Fermeture);
   }
   
   //support d'un itemset
  static  int Support(String item)
  {   int Support=0; 
	   for (int l=0; l<N; l++)
	   {
		    int i=0; boolean continuer=true;
		    while(i<item.length() && continuer==true)
		    {
		    	 if (data[l][Character.getNumericValue(item.charAt(i))]==1 && attributs.get(Character.getNumericValue(item.charAt(i)))!=false )
		    	 {
		    		 i++;
		    	 }
		    	 else 
		    	 {
		    		 continuer=false;
		    	 }
		    }
		   if (continuer==true)
			   {  Support++;
			   }
	   }
		System.out.println("\nLe support de "+item+" est : "+Support);

    return Support;
 	  
  }
  
  //retourne les lignes où apparait l'itemset
  static ArrayList<Integer> ApparItem(String item)
  {  ArrayList<Integer> Apparition=new ArrayList<Integer>();
	  
  for (int l=0; l<N; l++)
  {
	    int i=0; boolean continuer=true;
	    while(i<item.length() && continuer==true)
	    {
	    	 if (data[l][Character.getNumericValue(item.charAt(i))]==1 && 
	    			 attributs.get(Character.getNumericValue(item.charAt(i)))!=false)
	    	 {
	    		 i++;
	    	 }
	    	 else 
	    	 {
	    		 continuer=false;
	    	 }
	    }
	   if (continuer==true)
		   { Apparition.add(l)	;	      		    
		   }
  }
  return Apparition;
  }
  
	
   public static void main(String[] args) {
		readData();
		 for(int i=0; i<M; i++)
		  {    attributs.add(true);
		     		
		  }
		 
		 for(int i=0; i<M; i++)
		 {
			    if(Support(String.valueOf(i))<minSup)
				attributs.set(i, false);
         }
		 
		ArrayList item1=new ArrayList();
		item1=ConstruireItem(1);
		ConstruireItem(2);
		ConstruireItem(3);
		System.out.println("\nles itemsets fréquents fermés sont :");
		for(int i=0;  i<Fermetures.size(); i++)
		{
			System.out.print("  "+Fermetures.get(i));
		}
		

	}

}
