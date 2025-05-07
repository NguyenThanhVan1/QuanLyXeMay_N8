/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

/**
 *
 * @author lekha
 */
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;
public class NhanVienBUS 
{
    private ArrayList<NhanVienDTO> dsnv;
    public NhanVienBUS(int i1)
    {
        listNV();
    }
    public NhanVienBUS(){
    }
    public NhanVienDTO get(String MaNV)
    {
        for(NhanVienDTO nv : dsnv )
        {
            if(nv.getManv().equals(MaNV))
            {
                return nv;
            }
        }
        return null;
    }
    public void listNV()
    {
        NhanVienDAO nvDAO = new NhanVienDAO();
        dsnv = new ArrayList<>();
        dsnv = nvDAO.list();
    }
    public void addNV(NhanVienDTO sp)
    {
        dsnv.add(sp);
        NhanVienDAO nvDAO = new NhanVienDAO();
        nvDAO.add(sp);
    }

    public void deleteNV(String MaNV)
    {
        for(NhanVienDTO nv : dsnv )
        {
            if(nv.getManv().equals(MaNV))
            {
                dsnv.remove(nv);
                NhanVienDAO nvDAO = new NhanVienDAO();
                nvDAO.delete(MaNV);
                return;
            }
        }
    }
    public void setNV(NhanVienDTO s)
    {
        for(int i = 0 ; i < dsnv.size() ; i++)
        {
            if(dsnv.get(i).getManv().equals(s.getManv()))
            {
                dsnv.set(i, s);
                NhanVienDAO nvDAO = new NhanVienDAO();
                nvDAO.set(s);
                return;
            }
        }
    }
    public boolean check(String manv)
    {
        for(NhanVienDTO nv : dsnv)
        {
            if(nv.getManv().equals(manv))
            {
                return true;
            }
        }
        return false;
    }
    public ArrayList<NhanVienDTO> search(String manv,String ten,String chucvu,String dc,int namsinh)
    {
        ArrayList<NhanVienDTO> search = new ArrayList<>();
        manv = manv.isEmpty()?manv = "": manv;
        ten = ten.isEmpty()?ten = "": ten;
        chucvu = chucvu.isEmpty()?chucvu = "": chucvu;
        dc = dc.isEmpty()?dc = "": dc;
        for(NhanVienDTO nv : dsnv)
        {
            if( nv.getManv().contains(manv) && 
                nv.getHoten()   .contains(ten) &&
                nv.getChucvu().contains(chucvu) &&
                nv.getDiachi().contains(dc))
            {
                search.add(nv);
            }
        }
        return search;
    }
    public ArrayList<NhanVienDTO> getList() {
        return dsnv;
    }
}